package com.epam.brest.courses.rest_app.testConfig;

import com.epam.brest.courses.daoImpl.DevelopersJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoDtoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.Projects_DevelopersJdbcDaoImpl;
import com.epam.brest.courses.rest_app.exception.CustomExceptionHandler;
import com.epam.brest.courses.service.*;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import com.epam.brest.courses.service.excel.ExcelFileExportServiceImpl;
import com.epam.brest.courses.service.excel.ExcelFileImportService;
import com.epam.brest.courses.service.excel.ExcelFileImportServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class TestConfig {

    @Autowired
    private DriverManagerDataSource dataSource;

    @Bean
    public ExcelFileImportService fileImportService(){
        return new ExcelFileImportServiceImpl();
    }

    @Bean
    public ExcelFileExportService fileExportService(){
        return new ExcelFileExportServiceImpl();
    }

    @Bean
    public FakerService fakerService(){
        return new FakerServiceImpl();
    }

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
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Profile("h2")
    @Primary
    public DriverManagerDataSource dataSourceh2() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return  dataSource;
    }

    @Bean
    @Profile("mySql")
    public DriverManagerDataSource dataSourceMySql() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test_db");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return  dataSource;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public CustomExceptionHandler customExceptionHandler(){
        return new CustomExceptionHandler();
    }

}

