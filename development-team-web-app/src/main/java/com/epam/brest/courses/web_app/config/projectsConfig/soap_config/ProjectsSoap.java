package com.epam.brest.courses.web_app.config.projectsConfig.soap_config;

import com.epam.brest.courses.configSoap.ProjectConfiguration;
import com.epam.brest.courses.soapService.DevelopersClient;
import com.epam.brest.courses.soapService.ProjectsClient;
import com.epam.brest.courses.soapService.ProjectsDtoClient;
import com.epam.brest.courses.soapService.Projects_DevelopersClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@Profile("soap_service")
@ComponentScan("com.epam.brest.courses.configSoap")
public class ProjectsSoap {

    @Autowired
    public ProjectConfiguration projectConfiguration;

    @Autowired
    public Jaxb2Marshaller marshaller;

    @Autowired
    public ProjectsClient projectsClient;

    @Autowired
    public DevelopersClient developersClient;

    @Autowired
    public ProjectsDtoClient projectsDtoClient;

    @Autowired
    public Projects_DevelopersClient projects_developersClient;

}
