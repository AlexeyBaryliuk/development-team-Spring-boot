package com.epam.brest.courses.config;

import com.epam.brest.courses.soapService.DevelopersClient;
import com.epam.brest.courses.soapService.ProjectsClient;
import com.epam.brest.courses.soapService.ProjectsDtoClient;
import com.epam.brest.courses.soapService.Projects_DevelopersClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ProjectConfiguration {

    private final static String URI = "http://localhost:8088/ws/soap-xsd.wsdl";
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.epam.brest.courses.wsdl");
        return marshaller;
    }

    @Bean
    public ProjectsClient projectsClient(Jaxb2Marshaller marshaller) {
        ProjectsClient projectsClient = new ProjectsClient();
        projectsClient.setDefaultUri(URI);
        projectsClient.setMarshaller(marshaller);
        projectsClient.setUnmarshaller(marshaller);
        return projectsClient;
    }

    @Bean
    public DevelopersClient developersClient(Jaxb2Marshaller marshaller) {

        DevelopersClient developersClient = new DevelopersClient();
        developersClient.setDefaultUri(URI);
        developersClient.setMarshaller(marshaller);
        developersClient.setUnmarshaller(marshaller);
        return developersClient;
    }

    @Bean
    public Projects_DevelopersClient projects_developersClient(Jaxb2Marshaller marshaller) {

        Projects_DevelopersClient projects_developersClient = new Projects_DevelopersClient();
        projects_developersClient.setDefaultUri(URI);
        projects_developersClient.setMarshaller(marshaller);
        projects_developersClient.setUnmarshaller(marshaller);
        return projects_developersClient;
    }

    @Bean
    public ProjectsDtoClient projectsDtoClient(Jaxb2Marshaller marshaller) {

        ProjectsDtoClient projectsDtoClient = new ProjectsDtoClient();
        projectsDtoClient.setDefaultUri(URI);
        projectsDtoClient.setMarshaller(marshaller);
        projectsDtoClient.setUnmarshaller(marshaller);
        return projectsDtoClient;
    }
}

