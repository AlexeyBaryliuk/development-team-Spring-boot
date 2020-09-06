package com.epam.brest.courses.web_app.config.projectsConfig.soap_config;

import com.epam.brest.courses.soapService.DevelopersClient;
import com.epam.brest.courses.soapService.ProjectsClient;
import com.epam.brest.courses.soapService.ProjectsDtoClient;
import com.epam.brest.courses.soapService.Projects_DevelopersClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("soap_service")
//@ComponentScan(basePackages = {"com.epam.brest.courses.*"})
public class ProjectsSoap {

//    @Autowired
//    private final ProjectsClient projectsClient;
//    @Autowired
//    private final DevelopersClient developersClient;
//    @Autowired
//    private final Projects_DevelopersClient projects_developersClient;
//    @Autowired
//    private final ProjectsDtoClient projectsDtoClient;

//    public ProjectsSoap(ProjectsClient projectsClient
//            , DevelopersClient developersClient
//            , Projects_DevelopersClient projects_developersClient
//            , ProjectsDtoClient projectsDtoClient) {
//
//        this.projectsClient = projectsClient;
//        this.developersClient = developersClient;
//        this.projects_developersClient = projects_developersClient;
//        this.projectsDtoClient = projectsDtoClient;
//    }
    @Bean
    public ProjectsClient projectsClient(){
        return new ProjectsClient();
    }

    @Bean
    public DevelopersClient developersClient(){
        return new DevelopersClient();
    }

    @Bean
    public ProjectsDtoClient projectsDtoClient(){
        return new ProjectsDtoClient();
    }

    @Bean
    public Projects_DevelopersClient projects_developersClient(){
        return new Projects_DevelopersClient();
    }

//    @Bean
//    public ProjectsController projectsController(){
//        return new ProjectsController(projectsDtoClient
//                , projectsClient
//                , developersClient
//                , projects_developersClient);
//    }
//
//    @Bean
//    public DevelopersController developersController() {
//        return new DevelopersController(developersClient);
//    }
}
