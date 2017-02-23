package com.thagedy.footballclub.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis扫描接口
 * Created by Kaijia Wei on 2016/9/6.
 */
@Configuration
@AutoConfigureAfter(MybatisConfig.class)//由于MapperScannerConfiguer执行的较早，所以必须有本注解，否则会报错
public class MyBatisMapperScannerConfig {

    private final String SQL_SESSION_FACTORY = "sqlSessionFactory";

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY);
        mapperScannerConfigurer.setBasePackage("com.thagedy.footballclub.dao");
        return mapperScannerConfigurer;
    }
}