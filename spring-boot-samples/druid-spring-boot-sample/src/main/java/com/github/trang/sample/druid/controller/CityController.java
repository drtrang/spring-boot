package com.github.trang.sample.druid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.trang.sample.druid.model.City;
import com.github.trang.sample.druid.repository.CityRepository;

/**
 * CityController
 *
 * @author trang
 */
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/get/{id}")
    public City get(@PathVariable Long id) {
        return cityRepository.findById(id).orElse(null);
    }

}