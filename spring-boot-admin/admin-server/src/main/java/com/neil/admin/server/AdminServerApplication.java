package com.neil.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/6 14:39
 * @Version 1.0
 */
@EnableAdminServer
@SpringBootApplication(scanBasePackages = "com.neil")
public class AdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}
