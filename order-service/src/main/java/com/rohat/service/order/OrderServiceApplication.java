package com.rohat.service.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

import com.rohat.service.common.rabitmq.RabitmqConfiguration;

@EnableEurekaClient
@SpringBootApplication
@Import(RabitmqConfiguration.class)
@EntityScan(basePackages = {"com.rohat.service.common.order"})
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}