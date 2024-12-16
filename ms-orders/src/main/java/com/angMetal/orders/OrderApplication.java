package com.angMetal.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableKafkaStreams
@EnableJpaRepositories(basePackages = "com.angMetal.orders.repositories")
@EntityScan(basePackages = "com.angMetal.orders.entity")
@SpringBootApplication(scanBasePackages = "com.angMetal.orders")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
