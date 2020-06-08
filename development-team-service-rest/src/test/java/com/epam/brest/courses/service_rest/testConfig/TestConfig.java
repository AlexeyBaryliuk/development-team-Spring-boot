package com.epam.brest.courses.service_rest.testConfig;

import com.epam.brest.courses.service_rest.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    private String anyString = "";

    @Bean
    public Projects_DevelopersServiceRest projects_developersServiceRest(){ return new Projects_DevelopersServiceRest(anyString,restTemplate());}

    @Bean
    public DevelopersServiceRest developersServiceRest(){ return new DevelopersServiceRest(anyString,restTemplate());}

    @Bean
    public ProjectsDtoServiceRest projectsDtoServiceRest(){ return new ProjectsDtoServiceRest(anyString,restTemplate());}

    @Bean
    public ProjectsServiceRest projectsServiceRest(){ return new ProjectsServiceRest(anyString,restTemplate());}

    @Bean
    public FakerServiceRest fakerServiceRest(){ return new FakerServiceRest(anyString,restTemplate());}

    @Bean
    public RestTemplate restTemplate(){ return new RestTemplate();}

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
