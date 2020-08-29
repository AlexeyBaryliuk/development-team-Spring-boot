package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import service_soap.ProjectClient;

@Configuration
public class ProjectConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("/home/alexey/IdeaProjects/springExcel3/development-team-Spring-boot/development-team-soap-app/src/main/java/com/epam/brest/courses/soap_api");
        return marshaller;
    }

    @Bean
    public ProjectClient weatherClient(Jaxb2Marshaller marshaller) {
        ProjectClient projectClient = new ProjectClient();
        projectClient.setDefaultUri("http://localhost:8088/ws");
        projectClient.setMarshaller(marshaller);
        projectClient.setUnmarshaller(marshaller);
        return projectClient;
    }
}
