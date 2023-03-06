package com.neil.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// eureka客户端注册开启
@EnableDiscoveryClient
@EnableSwagger2
@SpringBootApplication(scanBasePackages = "com.neil")
public class SpringBootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
    }

}
