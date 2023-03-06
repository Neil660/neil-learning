package com.neil.rocketmq.producers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.neil")
public class RocketmqProducersApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqProducersApplication.class, args);
    }

}
