package com.neil.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.neil.common.dao")
@SpringBootApplication
public class NeilCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeilCommonApplication.class, args);
    }
}
