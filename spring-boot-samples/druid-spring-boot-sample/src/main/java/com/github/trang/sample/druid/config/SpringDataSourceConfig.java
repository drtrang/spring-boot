package com.github.trang.sample.druid.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.trang.autoconfigure.BeanCustomizer;

import lombok.extern.slf4j.Slf4j;

/**
 * 多数据源配置，只在 #{@code spring.profiles.active=dynamic} 时生效
 *
 * @author trang
 */
@Configuration
@Profile("dynamic")
@Slf4j
public class SpringDataSourceConfig {

    /**
     * 定制化 DruidDataSource，所有的数据源都会生效
     *
     * @return druidDataSourceCustomizer
     */
    @Bean
    public BeanCustomizer<DruidDataSource> druidDataSourceCustomizer() {
        return druidDataSource -> logger.info("DruidDataSourceCustomizer...");
    }

    /**
     * 构造 DynamicDataSource，指定数据源切换规则
     *
     * @param druidDataSourceMap druidDataSourceMap
     * @return dataSource
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(Map<String, DruidDataSource> druidDataSourceMap) {
        Map<String, DataSource> dataSourceMap = new HashMap<>(druidDataSourceMap);
        return new DynamicDataSource(dataSourceMap);
    }

}