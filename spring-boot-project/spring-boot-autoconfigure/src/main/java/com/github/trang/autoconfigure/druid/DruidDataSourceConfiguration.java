package com.github.trang.autoconfigure.druid;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jdbc.DataSourceUnwrapper;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.trang.autoconfigure.druid.datasource.DruidDataSourcePoolMetadata;

/**
 * Druid 数据源配置
 *
 * @author trang
 */
@Import(DruidDataSourceImportSelector.class)
class DruidDataSourceConfiguration {

    /**
     * Druid Metadata 自动配置，适用于 Metrics
     * 多数据源时只能取有 @Primary 修饰的 datasource 的值
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSourcePoolMetadataProvider druidDataSourcePoolMetadataProvider() {
        return dataSource -> {
            DruidDataSource druidDataSource = DataSourceUnwrapper.unwrap(dataSource, DruidDataSource.class);
            if (druidDataSource != null) {
                return new DruidDataSourcePoolMetadata(druidDataSource);
            }
            return null;
        };
    }

}