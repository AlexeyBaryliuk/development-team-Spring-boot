package com.epam.brest.courses.service.testConfig;

import com.epam.brest.courses.daoImpl.DevelopersJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoDtoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.Projects_DevelopersJdbcDaoImpl;
import com.epam.brest.courses.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class TestConfig {

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
}
