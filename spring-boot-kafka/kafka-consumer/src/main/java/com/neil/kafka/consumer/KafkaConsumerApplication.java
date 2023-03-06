package com.neil.kafka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.neil")
public class KafkaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }

}
