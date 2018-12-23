package com.github.trang.sample.druid;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.trang.sample.druid.repository.CityRepository;

/**
 * BaseTest
 *
 * @author trang
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DruidJpaApplication.class)
public abstract class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger("DruidTest");

    @Autowired(required = false)
    protected CityRepository cityRepository;

}