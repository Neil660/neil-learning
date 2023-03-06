package com.neil.kafka.producers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.neil")
public class KafkaProducersApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducersApplication.class, args);
    }

}
