package com.epam.brest.courses.rest_soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.epam.brest.courses.*")
@ComponentScan("com.epam.brest.courses.*")
@EntityScan("com.epam.brest.courses.*")
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class);
    }
}
