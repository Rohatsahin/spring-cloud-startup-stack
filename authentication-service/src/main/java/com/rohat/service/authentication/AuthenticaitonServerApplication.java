package com.rohat.service.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan(basePackages = {"com.rohat.service.common.authenticaiton"})
public class AuthenticaitonServerApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(AuthenticaitonServerApplication.class, args);
    }
}