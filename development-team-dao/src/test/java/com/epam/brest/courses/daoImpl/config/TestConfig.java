package com.epam.brest.courses.daoImpl.config;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.dao.ProjectsDaoDto;
import com.epam.brest.courses.dao.Projects_DevelopersDao;
import com.epam.brest.courses.daoImpl.DevelopersJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoDtoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.Projects_DevelopersJdbcDaoImpl;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class TestConfig {

    @Autowired
    private DriverManagerDataSource dataSource;

    @Bean
    public DevelopersDao developersJdbcDao() {
        return new DevelopersJdbcDaoImpl(namedParameterJdbcTemplate());
    }

    @Bean
    public ProjectsDao projectJdbcDao() {
        return new ProjectJdbcDaoImpl(namedParameterJdbcTemplate());
    }

    @Bean
    public ProjectsDaoDto projectJdbcDaoDto() {
        return new ProjectJdbcDaoDtoImpl(namedParameterJdbcTemplate());}

    @Bean
    public Projects_DevelopersDao projects_developersJdbcDao() {
        return new Projects_DevelopersJdbcDaoImpl(namedParameterJdbcTemplate());}

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/db.liquibase-changeLog.master.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    @Bean
    @Profile("h2")
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


}