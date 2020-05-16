package com.epam.brest.courses.rest_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.epam.brest.courses.*"})
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class);
    }
}