package com.epam.brest.courses.service_rest.testConfig;

import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.FakerService;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.xml.XmlFileExportService;
import com.epam.brest.courses.service.xml.XmlFileImportService;
import com.epam.brest.courses.service_rest.*;
import com.epam.brest.courses.service_rest.xml.XmlFileExportServiceRest;
import com.epam.brest.courses.service_rest.xml.XmlFileImportServiceRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    private String anyString = "";

    @Bean
    public XmlFileImportService xmlFileImportServiceRest(){
        return new XmlFileImportServiceRest(anyString,restTemplate());
    }

    @Bean
    public XmlFileExportService xmlFileExportServiceRest(){
        return new XmlFileExportServiceRest(anyString,restTemplate());
    }

    @Bean
    public Projects_DevelopersServiceRest projects_developersServiceRest(){ return new Projects_DevelopersServiceRest(anyString,restTemplate());}

    @Bean
    public DevelopersService developersServiceRest(){ return new DevelopersServiceRest(anyString,restTemplate());}

    @Bean
    public ProjectsDtoService projectsDtoServiceRest(){ return new ProjectsDtoServiceRest(anyString,restTemplate());}

    @Bean
    public ProjectsService projectsServiceRest(){ return new ProjectsServiceRest(anyString,restTemplate());}

    @Bean
    public FakerService fakerServiceRest(){ return new FakerServiceRest(anyString,restTemplate());}

    @Bean
    public RestTemplate restTemplate(){ return new RestTemplate();}

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
