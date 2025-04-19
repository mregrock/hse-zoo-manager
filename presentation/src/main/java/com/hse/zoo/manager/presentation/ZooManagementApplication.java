package com.hse.zoo.manager.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.hse.zoo.manager.presentation", 
    "com.hse.zoo.manager.application", 
    "com.hse.zoo.manager.infrastructure", 
    "com.hse.zoo.manager.domain"
})

public class ZooManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZooManagementApplication.class, args);
        System.out.println("\nSwagger UI available at http://localhost:8080/swagger-ui.html");
        System.out.println("Application started successfully!");
    }
}
