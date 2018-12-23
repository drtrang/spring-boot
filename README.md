# Spring Boot

[![Build Status](https://img.shields.io/travis/drtrang/parent/boot2.svg?style=flat-square)](https://www.travis-ci.org/drtrang/parent)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.drtrang/parent2.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/com.github.drtrang/parent2)
[![GitHub Release](https://img.shields.io/github/release/drtrang/parent.svg?style=flat-square)](https://github.com/drtrang/parent/releases)
[![License](http://img.shields.io/badge/license-apache%202-blue.svg?style=flat-square)](http://www.apache.org/licenses/LICENSE-2.0)

各个项目的公共依赖和插件，只支持 Java8。

## 依赖
```xml
<!-- spring boot 2.x -->
<dependency>
    <groupId>com.github.drtrang</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>2.0.0</version>
</dependency>
```

## 迭代计划
跟随 spring-boot 的版本节奏，每当 spring-boot 发布新版本后会第一时间更新。

## 工程结构
```
spring-boot
    ├── spring-boot-bom
    ├── spring-boot-project
    │   ├── spring-boot-autoconfigure
    │   ├── spring-boot-dependencies
    │   ├── spring-boot-parent
    │   └── spring-boot-starters
    │       └── xxx-spring-boot-starter
    └── spring-boot-samples
        └── xxx-spring-boot-sample
```