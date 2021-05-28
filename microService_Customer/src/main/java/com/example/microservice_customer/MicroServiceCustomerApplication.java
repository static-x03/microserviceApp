package com.example.microservice_customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroServiceCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCustomerApplication.class, args);
    }

}
