package com.github.trang.autoconfigure.druid;

import static com.github.trang.autoconfigure.druid.DruidConditions.DYNAMIC_PROPERTIES;
import static java.util.Collections.emptyMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.trang.autoconfigure.BeanCustomizer;
import com.github.trang.autoconfigure.druid.datasource.DruidDataSource2;
import com.github.trang.autoconfigure.druid.util.CharMatcher;

import lombok.extern.slf4j.Slf4j;


/**
 * 数据源选择器
 * 当配置文件中存在 spring.datasource.druid.data-sources 属性时为多数据源
 * 不存在则为单数据源
 *
 * @author trang
 */
@Slf4j
class DruidDataSourceImportSelector implements ImportSelector, EnvironmentAware {

    private static final String BEAN_NAME = "dataSource";
    private static final String BEAN_SUFFIX = "DataSource";

    private boolean isDynamic;

    @Override
    public void setEnvironment(Environment environment) {
        this.isDynamic = Binder.get(environment)
                .bind(DYNAMIC_PROPERTIES, Bindable.mapOf(String.class, Object.class))
                .isBound();
    }

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        Stream.Builder<Class<?>> imposts = Stream.<Class<?>>builder().add(DruidDataSourceBeanPostProcessor.class);
        imposts.add(!isDynamic ? SingleDataSourceRegistrar.class : DynamicDataSourceRegistrar.class);
        return imposts.build().map(Class::getName).toArray(String[]::new);
    }

    /**
     * 单数据源注册
     *
     * @author trang
     */
    static class SingleDataSourceRegistrar implements ImportBeanDefinitionRegistrar {

        @Override
        public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
            if (!registry.containsBeanDefinition(BEAN_NAME)) {
                registry.registerBeanDefinition(BEAN_NAME, genericDruidBeanDefinition());
            }
        }

    }

    /**
     * 多数据源注册
     *
     * @author trang
     */
    static class DynamicDataSourceRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

        private Map<String, Object> dataSources;

        @Override
        public void setEnvironment(Environment environment) {
            this.dataSources = Binder.get(environment)
                    .bind(DYNAMIC_PROPERTIES, Bindable.mapOf(String.class, Object.class))
                    .orElse(emptyMap());
        }

        @Override
        public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
            this.dataSources.keySet().forEach(dataSourceName -> {
                // 注册 BeanDefinition
                String beanName = CharMatcher.separatedToCamel().apply(dataSourceName);
                if (!registry.containsBeanDefinition(beanName)) {
                    registry.registerBeanDefinition(beanName, genericDruidBeanDefinition());
                }
                // 注册以 DataSource 为后缀的别名
                if (!StringUtils.endsWithIgnoreCase(beanName, BEAN_SUFFIX)) {
                    registry.registerAlias(beanName, beanName + BEAN_SUFFIX);
                }
            });
        }

    }

    /**
     * 构造 BeanDefinition，通过 DruidDataSource2 实现继承 'spring.datasource.druid' 的配置
     *
     * @return BeanDefinition druidBeanDefinition
     */
    private static BeanDefinition genericDruidBeanDefinition() {
        return BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource2.class)
                .setInitMethodName("init")
                .setDestroyMethodName("close")
                .getBeanDefinition();
    }

    /**
     * DruidDataSource 的 Bean 处理器，将各数据源的自定义配置绑定到 Bean
     *
     * @author trang
     */
    static class DruidDataSourceBeanPostProcessor implements EnvironmentAware, BeanPostProcessor {

        private final List<BeanCustomizer<DruidDataSource>> customizers;
        private Environment environment;
        private Map<String, Object> dataSources;

        public DruidDataSourceBeanPostProcessor(ObjectProvider<List<BeanCustomizer<DruidDataSource>>> customizers) {
            this.customizers = customizers.getIfAvailable(ArrayList::new);
        }

        @Override
        public void setEnvironment(Environment environment) {
            this.environment = environment;
            this.dataSources = Binder.get(environment)
                    .bind(DYNAMIC_PROPERTIES, Bindable.mapOf(String.class, Object.class))
                    .orElse(emptyMap());
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof DruidDataSource) {
                if (dataSources.isEmpty()) {
                    logger.info("druid single data-source({}) init...", beanName);
                } else {
                    logger.info("druid dynamic data-source({}) init...", beanName);
                }
                // 设置 Druid 名称
                DruidDataSource druidDataSource = (DruidDataSource) bean;
                druidDataSource.setName(beanName);
                // 将 'spring.datasource.druid.data-sources.${name}' 的配置绑定到 Bean
                if (!dataSources.isEmpty()) {
                    Binder.get(environment).bind(DYNAMIC_PROPERTIES + "." + beanName, Bindable.ofInstance(druidDataSource));
                }
                // 定制化配置，拥有最高优先级，会覆盖之前已有的配置
                customizers.forEach(customizer -> customizer.customize(druidDataSource));
            }
            return bean;
        }

    }

}