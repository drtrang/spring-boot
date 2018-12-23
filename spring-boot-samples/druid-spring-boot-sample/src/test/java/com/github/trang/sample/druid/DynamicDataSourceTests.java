package com.github.trang.sample.druid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ActiveProfiles;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.trang.autoconfigure.druid.datasource.DruidDataSource2;
import com.github.trang.sample.druid.config.DynamicDataSource;
import com.github.trang.sample.druid.model.City;

/**
 * 动态数据源测试
 *
 * @author trang
 */
@ActiveProfiles("dynamic")
public class DynamicDataSourceTests extends BaseTest {

    @Autowired
    private DruidDataSource masterDataSource;
    @Autowired
    private DruidDataSource slaveDataSource;
    @Autowired
    private DynamicDataSource dataSource;

    @Test
    public void testMasterDataSource() {
        assertEquals(0, masterDataSource.getInitialSize());
        assertEquals(0, masterDataSource.getMinIdle());
        assertEquals(50, masterDataSource.getMaxActive());
        assertEquals(30000, masterDataSource.getMaxWait());
        assertEquals(60000, masterDataSource.getTimeBetweenEvictionRunsMillis());
        assertEquals(1800000, masterDataSource.getMinEvictableIdleTimeMillis());
        assertEquals(25200000, masterDataSource.getMaxEvictableIdleTimeMillis());
        assertEquals("SELECT 'x'", masterDataSource.getValidationQuery());
        assertEquals(true, masterDataSource.isTestWhileIdle());
        assertEquals(false, masterDataSource.isTestOnBorrow());
        assertEquals(false, masterDataSource.isTestOnReturn());
        assertEquals(50, masterDataSource.getMaxPoolPreparedStatementPerConnectionSize());
        assertEquals(true, masterDataSource.isUseGlobalDataSourceStat());
    }

    @Test
    public void testSlaveDataSource() {
        assertEquals(0, slaveDataSource.getInitialSize());
        assertEquals(0, slaveDataSource.getMinIdle());
        assertEquals(25, slaveDataSource.getMaxActive());
        assertEquals(30000, slaveDataSource.getMaxWait());
        assertEquals(60000, slaveDataSource.getTimeBetweenEvictionRunsMillis());
        assertEquals(1800000, slaveDataSource.getMinEvictableIdleTimeMillis());
        assertEquals(25200000, slaveDataSource.getMaxEvictableIdleTimeMillis());
        assertEquals("SELECT 'x'", slaveDataSource.getValidationQuery());
        assertEquals(true, slaveDataSource.isTestWhileIdle());
        assertEquals(false, slaveDataSource.isTestOnBorrow());
        assertEquals(false, slaveDataSource.isTestOnReturn());
        assertEquals(25, slaveDataSource.getMaxPoolPreparedStatementPerConnectionSize());
        assertEquals(true, slaveDataSource.isUseGlobalDataSourceStat());
    }

    @Test
    public void testDynamicDataSource() {
        assertEquals(dataSource.getTargetDataSources().size(), 2);
    }

    @Test
    @Repeat(2)
    public void testOne() {
        cityRepository.findById(1L)
                .ifPresent(city -> logger.info("{}", city));
    }

    @Test
    @Repeat(2)
    public void testAll() {
        List<City> cities = cityRepository.findAll();
        cities.stream().map(City::toString).forEach(logger::info);
        assertEquals(5, cities.size());
    }

    @Autowired(required = false)
    private Map<String, DruidDataSource> dataSourceMap;
    @Autowired(required = false)
    private Map<String, DruidDataSource> druidDataSourceMap;
    @Autowired(required = false)
    private Map<String, DruidDataSource> foolDataSourceMap;
    @Autowired(required = false)
    private List<DruidDataSource> dataSourceList;
    @Autowired(required = false)
    private List<DruidDataSource> dataSources;
    @Autowired(required = false)
    private List<DruidDataSource> druidDataSourceList;
    @Autowired(required = false)
    private List<DruidDataSource> druidDataSources;
    @Autowired(required = false)
    private List<DruidDataSource> foolDataSources;
    @Autowired(required = false)
    private Collection<DruidDataSource> druidDataSourceCollection;
    @Autowired(required = false)
    private Set<DruidDataSource> druidDataSourceSet;
    @Autowired(required = false)
    private DruidDataSource[] druidDataSourceArray;


    @Autowired(required = false)
    private Map<String, DruidDataSource2> dataSource2Map;
    @Autowired(required = false)
    private Map<String, DruidDataSource2> druidDataSource2Map;
    @Autowired(required = false)
    private Map<String, DruidDataSource2> foolDataSource2Map;
    @Autowired(required = false)
    private List<DruidDataSource2> dataSource2List;
    @Autowired(required = false)
    private List<DruidDataSource2> dataSources2;
    @Autowired(required = false)
    private List<DruidDataSource2> druidDataSource2List;
    @Autowired(required = false)
    private List<DruidDataSource2> druidDataSources2;
    @Autowired(required = false)
    private List<DruidDataSource2> foolDataSources2;
    @Autowired(required = false)
    private Collection<DruidDataSource2> druidDataSource2Collection;
    @Autowired(required = false)
    private Set<DruidDataSource2> druidDataSource2Set;
    @Autowired(required = false)
    private DruidDataSource2[] druidDataSource2Array;

    @Test
    public void testBean() {
        assertNotNull(dataSourceMap);
        assertNotNull(druidDataSourceMap);
        assertNotNull(foolDataSourceMap);
        assertNotNull(dataSourceList);
        assertNotNull(dataSources);
        assertNotNull(druidDataSourceList);
        assertNotNull(druidDataSources);
        assertNotNull(foolDataSources);
        assertNotNull(druidDataSourceCollection);
        assertNotNull(druidDataSourceSet);
        assertNotNull(druidDataSourceArray);

        assertNotNull(dataSource2Map);
        assertNotNull(druidDataSource2Map);
        assertNotNull(foolDataSource2Map);
        assertNotNull(dataSource2List);
        assertNotNull(dataSources2);
        assertNotNull(druidDataSource2List);
        assertNotNull(druidDataSources2);
        assertNotNull(foolDataSources2);
        assertNotNull(druidDataSource2Collection);
        assertNotNull(druidDataSource2Set);
        assertNotNull(druidDataSource2Array);
    }

}