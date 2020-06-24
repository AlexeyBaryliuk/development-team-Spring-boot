package com.epam.brest.courses.web_app.config.projectsConfig;

import com.epam.brest.courses.service.excel.ExcelFileExportService;
import com.epam.brest.courses.service.excel.ExcelFileImportService;
import com.epam.brest.courses.service_rest.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:application.properties")
public class ProjectConfig {

    @Value("${point.start-url}")
    private String startUrl;

    @Value("${point.projects}")
    private String projects;

    @Value("${point.projectsDto}")
    private String projectsDto;

    @Value("${point.developers}")
    private String developers;

    @Value("${point.projects_developers}")
    private String projects_developers;

    @Value("${point.faker}")
    private String faker;

    private StringBuilder newUrl;

    @Bean
    public ExcelFileExportService getExcelFileExportService(){

        return new ExcelFileExportServiceRest(startUrl, restTemplate());
    }

    @Bean
    @Primary
    public FakerServiceRest fakerServiceRest() {

        newUrl= new StringBuilder();
        String developersUrl = newUrl.append(startUrl).append(faker).toString();
        return new FakerServiceRest(developersUrl,restTemplate());
    }

    @Bean
    @Primary
    public ExcelFileImportService excelFileImportService(){

        return new ExcelFileImportServiceRest(startUrl,restTemplate());
    }

    @Bean
    @Primary
    public DevelopersServiceRest developersServiceRest() {

         newUrl= new StringBuilder();
       String developersUrl = newUrl.append(startUrl).append(developers).toString();
        return new DevelopersServiceRest(developersUrl,restTemplate());
    }

    @Bean
    @Primary
    public ProjectsServiceRest projectsServiceRest() {

        newUrl= new StringBuilder();
        String projectsUrl = newUrl.append(startUrl).append(projects).toString();
        return new ProjectsServiceRest(projectsUrl,restTemplate());
    }

    @Bean
    @Primary
    public ProjectsDtoServiceRest projectsDtoServiceRest() {

        newUrl= new StringBuilder();
        String projectsDtoUrl = newUrl.append(startUrl).append(projectsDto).toString();
        return new ProjectsDtoServiceRest(projectsDtoUrl,restTemplate());
    }

    @Bean
    @Primary
    public Projects_DevelopersServiceRest projects_developersServiceRest() {

        newUrl= new StringBuilder();
       String projects_DevelopersUrl = newUrl.append(startUrl).append(projects_developers).toString();
        return new Projects_DevelopersServiceRest(projects_DevelopersUrl,restTemplate());
    }


    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
