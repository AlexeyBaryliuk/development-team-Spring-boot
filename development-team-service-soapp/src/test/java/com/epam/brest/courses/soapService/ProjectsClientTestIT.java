package com.epam.brest.courses.soapService;

import com.epam.brest.courses.configSoap.ProjectConfiguration;
import com.epam.brest.courses.model.Projects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//The server is running before executing this test
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProjectConfiguration.class
        , loader = AnnotationConfigContextLoader.class)
class ProjectsClientTestIT {

    @Autowired
    private ProjectsClient projectsClient;

    @Test
    void shouldFindAllProjects() {

        List<Projects> projectsList = projectsClient.findAll();

        assertNotNull(projectsList);

    }

    @Test
    void findByProjectId() {
        Integer projectId = 1;
        Projects project = projectsClient.findByProjectId(projectId).get();
        System.out.println("///////////////////// = " + project);
        assertEquals(project.getProjectId(),projectId);
    }

    @Test
    void update() throws DatatypeConfigurationException {
        Projects project = projectsClient.findAll().get(0);
        project.setDescription("Test");
        projectsClient.update(project);
        Projects projectAfter = projectsClient.findAll().get(0);
        assertEquals("Test", projectAfter.getDescription());
    }

    @Test
    void create() throws DatatypeConfigurationException {

        Integer numBeforAdded = projectsClient.findAll().size();

        Projects project = new Projects();
        project.setDescription("Test");
        project.setDateAdded(LocalDate.now());
        projectsClient.create(project);
        Integer numAfterAdded = projectsClient.findAll().size();

        assertEquals(numBeforAdded + 1, numAfterAdded );
    }

    @Test
    void createF() {
    }

    @Test
    void delete() {

        Integer numRow = projectsClient.delete(0);

        assertTrue(numRow.equals(0));
    }
}
