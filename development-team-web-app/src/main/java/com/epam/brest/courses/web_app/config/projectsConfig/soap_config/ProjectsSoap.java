package com.epam.brest.courses.web_app.config.projectsConfig.soap_config;

import com.epam.brest.courses.soap_service_api.ProjectsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("soap_service")
@ComponentScan(basePackages = {"com.epam.brest.courses.*"})
public class ProjectsSoap {

    private ProjectsClient projectsClient;

    public ProjectsSoap(ProjectsClient projectsClient) {
        this.projectsClient = projectsClient;
    }

    @Bean
    public ProjectsClient projectClient(){
        return new ProjectsClient();
    }

}
