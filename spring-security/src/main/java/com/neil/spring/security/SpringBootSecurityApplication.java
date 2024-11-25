package com.neil.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/20 10:47
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.neil")
public class SpringBootSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityApplication.class, args);
    }
}
