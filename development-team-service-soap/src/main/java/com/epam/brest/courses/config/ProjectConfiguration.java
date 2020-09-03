package com.epam.brest.courses.config;

import com.epam.brest.courses.soap_service_api.ProjectClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ProjectConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.epam.brest.courses.wsdl");
        return marshaller;
    }

    @Bean
    public ProjectClient projectsClient(Jaxb2Marshaller marshaller) {
        ProjectClient projectClient = new ProjectClient();
        projectClient.setDefaultUri("http://localhost:8088/ws");
        projectClient.setMarshaller(marshaller);
        projectClient.setUnmarshaller(marshaller);
        return projectClient;
    }
}
