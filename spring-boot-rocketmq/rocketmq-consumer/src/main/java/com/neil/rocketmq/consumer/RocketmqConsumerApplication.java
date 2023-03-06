package com.neil.rocketmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.neil")
public class RocketmqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqConsumerApplication.class, args);
    }

}
