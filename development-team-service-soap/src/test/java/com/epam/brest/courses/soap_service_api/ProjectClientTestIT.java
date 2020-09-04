package com.epam.brest.courses.soap_service_api;

import com.epam.brest.courses.config.ProjectConfiguration;
import com.epam.brest.courses.model.Projects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//The server is running before executing this test
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProjectConfiguration.class
        , loader = AnnotationConfigContextLoader.class)
class ProjectClientTestIT {

    @Autowired
    private ProjectClient projectClient;

    @Test
    void shouldFindAllProjects() {

        List<Projects> projectsList = projectClient.findAll();

        assertNotNull(projectsList);

    }

    @Test
    void findByDeveloperId() {
    }

    @Test
    void update() {
    }

    @Test
    void create() {
    }

    @Test
    void createF() {
    }

    @Test
    void delete() {
    }
}
