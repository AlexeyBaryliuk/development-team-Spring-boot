package com.epam.brest.courses.web_app.testConfig;

import com.epam.brest.courses.daoImpl.DevelopersJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoDtoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.Projects_DevelopersJdbcDaoImpl;
import com.epam.brest.courses.service.DevelopersServiceImpl;
import com.epam.brest.courses.service.ProjectsDtoServiceImpl;
import com.epam.brest.courses.service.ProjectsServiceImpl;
import com.epam.brest.courses.service.Projects_DevelopersServiceImpl;
import com.epam.brest.courses.service_rest.DevelopersServiceRest;
import com.epam.brest.courses.service_rest.ProjectsDtoServiceRest;
import com.epam.brest.courses.service_rest.ProjectsServiceRest;
import com.epam.brest.courses.service_rest.Projects_DevelopersServiceRest;
import com.epam.brest.courses.rest_app.controllers.DevelopersController;
import com.epam.brest.courses.web_app.validators.DevelopersValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    private String anyString = "";

    @Bean
    public DevelopersJdbcDaoImpl developersJdbcDao() {
        return new DevelopersJdbcDaoImpl(namedParameterJdbcTemplate());
    }

    @Bean
    public DevelopersServiceImpl developersServiceImpl(){
        return new DevelopersServiceImpl(developersJdbcDao());
    }

    @Bean
    public ProjectJdbcDaoImpl projectJdbcDao() {
        return new ProjectJdbcDaoImpl(namedParameterJdbcTemplate());
    }

    @Bean
    public ProjectsServiceImpl projectsService(){
        return new ProjectsServiceImpl(projectJdbcDao());
    }

    @Bean
    public Projects_DevelopersJdbcDaoImpl projects_developersJdbcDao() {
        return new Projects_DevelopersJdbcDaoImpl(namedParameterJdbcTemplate());}

    @Bean
    public Projects_DevelopersServiceImpl projects_developersService(){
        return new Projects_DevelopersServiceImpl(projects_developersJdbcDao());
    }

    @Bean
    public ProjectJdbcDaoDtoImpl projectJdbcDaoDto() {
        return new ProjectJdbcDaoDtoImpl(namedParameterJdbcTemplate());}

    @Bean
    public ProjectsDtoServiceImpl projectsDtoService(){
        return new ProjectsDtoServiceImpl(projectJdbcDaoDto());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
//
    @Bean
    @Profile("test")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return  dataSource;
    }


//    @Bean
//    public ObjectMapper objectMapper(){
//        return new ObjectMapper();
//    }

    @Bean
    public DevelopersController developersController(){
        return new DevelopersController(developersServiceImpl());
    }

//    @Bean
//    public Projects_DevelopersServiceRest projects_developersServiceRest(){ return new Projects_DevelopersServiceRest(anyString,restTemplate());}
//
//    @Bean
//    public DevelopersServiceRest developersServiceRest(){ return new DevelopersServiceRest(anyString,restTemplate());}
//
//    @Bean
//    public ProjectsDtoServiceRest projectsDtoServiceRest(){ return new ProjectsDtoServiceRest(anyString,restTemplate());}
//
//    @Bean
//    public ProjectsServiceRest projectsServiceRest(){ return new ProjectsServiceRest(anyString,restTemplate());}
//
//    @Bean
//    public RestTemplate restTemplate(){ return new RestTemplate();}

    @Bean
    public DevelopersValidator developersValidator(){return new DevelopersValidator();}

}

