package com.spring.boot.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching //开启缓存
@SpringBootApplication
public class SpringBootRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisApplication.class, args);
    }

}
