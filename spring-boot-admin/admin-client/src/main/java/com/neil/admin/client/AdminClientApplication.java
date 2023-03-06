package com.neil.admin.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/6 14:32
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.neil")
public class AdminClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminClientApplication.class, args);
    }
}
