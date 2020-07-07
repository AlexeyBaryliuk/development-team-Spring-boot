package com.epam.brest.courses.service.testConfig;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.dao.ProjectsDaoDto;
import com.epam.brest.courses.dao.Projects_DevelopersDao;
import com.epam.brest.courses.daoImpl.DevelopersJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoDtoImpl;
import com.epam.brest.courses.daoImpl.ProjectJdbcDaoImpl;
import com.epam.brest.courses.daoImpl.Projects_DevelopersJdbcDaoImpl;
import com.epam.brest.courses.service.*;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import com.epam.brest.courses.service.excel.ExcelFileExportServiceImpl;
import com.epam.brest.courses.service.excel.ExcelFileImportService;
import com.epam.brest.courses.service.excel.ExcelFileImportServiceImpl;
import com.epam.brest.courses.service.xml.*;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class TestConfig {

    @Autowired
    private DriverManagerDataSource dataSource;

    @Bean
    public XmlFileImportService xmlFileImportService(){
        return new XmlFileImportServiceImpl();
    }

    @Bean
    public XmlFileExportService xmlFileExportService(){
        return new XmlFileExportServiceImpl();
    }

    @Bean
    public ArchiveService archiveService(){
        return new ArchiveService();
    }

    @Bean
    public CheckFolder checkFolder(){
        return new CheckFolder();

    }

    @Bean
    public ExcelFileImportService excelFileImportService(){
        return new ExcelFileImportServiceImpl();
    }

    @Bean
    public ExcelFileExportService excelFileExportService(){
        return new ExcelFileExportServiceImpl();
    }

    @Bean
    public FakerService fakerService(){
        return new FakerServiceImpl();
    }

    @Bean
    public DevelopersDao developersJdbcDao() {
        return new DevelopersJdbcDaoImpl(namedParameterJdbcTemplate());
    }

    @Bean
    public DevelopersService developersServiceImpl(){
        return new DevelopersServiceImpl(developersJdbcDao());
    }

    @Bean
    public ProjectsDao projectJdbcDao() {
        return new ProjectJdbcDaoImpl(namedParameterJdbcTemplate());
    }

    @Bean
    public ProjectsService projectsService(){
        return new ProjectsServiceImpl(projectJdbcDao());
    }

    @Bean
    public Projects_DevelopersDao projects_developersJdbcDao() {
        return new Projects_DevelopersJdbcDaoImpl(namedParameterJdbcTemplate());}

    @Bean
    public Projects_DevelopersService projects_developersService(){
        return new Projects_DevelopersServiceImpl(projects_developersJdbcDao());
    }

    @Bean
    public ProjectsDaoDto projectJdbcDaoDto() {
        return new ProjectJdbcDaoDtoImpl(namedParameterJdbcTemplate());}

    @Bean
    public ProjectsDtoService projectsDtoService(){
        return new ProjectsDtoServiceImpl(projectJdbcDaoDto());
    }

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
