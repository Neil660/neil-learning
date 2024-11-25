package com.neil.redission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/1 10:48
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.neil")
public class SpringBootRedisRedissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisRedissionApplication.class, args);
    }
}
