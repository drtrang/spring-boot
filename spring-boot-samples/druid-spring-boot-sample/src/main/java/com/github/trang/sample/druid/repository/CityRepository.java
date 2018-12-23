package com.github.trang.sample.druid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.trang.sample.druid.model.City;

/**
 * CityRepository
 *
 * @author trang
 */
public interface CityRepository extends JpaRepository<City, Long> {

}