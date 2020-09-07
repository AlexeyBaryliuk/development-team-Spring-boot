package com.epam.brest.courses.soapService;

import com.epam.brest.courses.configSoap.ProjectConfiguration;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
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
class ProjectsDtoClientTestIT {

    @Autowired
    private ProjectsDtoClient projectsDtoClient;

    @Autowired
    private ProjectsClient projectsClient;

    @Test
    void findAllByDateAddedBetween() throws DatatypeConfigurationException {

        String description = "Test";
        Projects project = new Projects();
        project.setDateAdded(LocalDate.now());
        project.setDescription(description);
        Integer projectId = projectsClient.create(project);
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now().plusDays(1);
        List<ProjectsDto> projectsDtoList = projectsDtoClient.findAllByDateAddedBetween(from, to);
        System.out.println("Project = " + projectId);
        System.out.println("+++++Project = " + projectsDtoList.size());
        assertTrue(projectsDtoList.size() == 1);

        assertEquals(description, projectsDtoList.get(0).getDescription());
        projectsClient.delete(projectId);
    }

    @Test
    void countOfDevelopers() throws DatatypeConfigurationException {
        List<ProjectsDto> projectsDtoList = projectsDtoClient.countOfDevelopers();

        assertTrue(projectsDtoList.size() > 0);
    }
}