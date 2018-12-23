package com.github.trang.sample.druid;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.trang.sample.druid.model.City;

/**
 * 单数据源测试
 *
 * @author trang
 */
@ActiveProfiles("default")
public class SingleDataSourceTests extends BaseTest {

    @Autowired
    private DruidDataSource dataSource;

    @Test
    public void testSingleDataSource() {
        assertEquals(0, dataSource.getInitialSize());
        assertEquals(0, dataSource.getMinIdle());
        assertEquals(8, dataSource.getMaxActive());
        assertEquals(30000, dataSource.getMaxWait());
        assertEquals(60000, dataSource.getTimeBetweenEvictionRunsMillis());
        assertEquals(1800000, dataSource.getMinEvictableIdleTimeMillis());
        assertEquals(25200000, dataSource.getMaxEvictableIdleTimeMillis());
        assertEquals("SELECT 'x'", dataSource.getValidationQuery());
        assertEquals(true, dataSource.isTestWhileIdle());
        assertEquals(false, dataSource.isTestOnBorrow());
        assertEquals(false, dataSource.isTestOnReturn());
        assertEquals(10, dataSource.getMaxPoolPreparedStatementPerConnectionSize());
        assertEquals(true, dataSource.isUseGlobalDataSourceStat());
    }

    @Test
    public void testOne() {
        cityRepository.findById(1L).ifPresent(city -> logger.info("{}", city));
    }

    @Test
    public void testAll() {
        List<City> cities = cityRepository.findAll();
        cities.stream().map(City::toString).forEach(logger::info);
        Assert.assertEquals(5, cities.size());
    }

}