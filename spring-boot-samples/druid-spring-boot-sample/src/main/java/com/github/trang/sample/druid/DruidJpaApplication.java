package com.github.trang.sample.druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.trang.sample.druid.model.City;
import com.github.trang.sample.druid.repository.CityRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * DruidJpaApplication
 *
 * @author trang
 */
@SpringBootApplication
@Slf4j
public class DruidJpaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.setProperty("druid.logType", "slf4j");
        SpringApplication.run(DruidJpaApplication.class, args);
    }

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void run(String... args) {
        cityRepository.findAll().stream()
                .map(City::toString)
                .forEach(logger::info);
    }

}