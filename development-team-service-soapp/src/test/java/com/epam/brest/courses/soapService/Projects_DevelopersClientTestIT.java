package com.epam.brest.courses.soapService;

import com.epam.brest.courses.configSoap.ProjectConfiguration;
import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.Projects_Developers;
import com.epam.brest.courses.service.Projects_DevelopersService;
import org.apache.commons.lang.RandomStringUtils;
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
import java.util.Optional;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;
//The server is running before executing this test
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProjectConfiguration.class
        , loader = AnnotationConfigContextLoader.class)
class Projects_DevelopersClientTestIT {

    @Autowired
    private Projects_DevelopersClient projects_developersClient;

    @Autowired
    private DevelopersClient developersClient;

    @Autowired
    private ProjectsClient projectsClient;

    @Test
    void shouldSelectDevelopersFromProjects_Developers() {
        List<Developers> developersList = projects_developersClient
                .selectDevelopersFromProjects_Developers(1);

        assertTrue(developersList.size() > 0);
    }

    @Test
    void shouldAddDeveloperToProjects_Developers() throws DatatypeConfigurationException {
        Integer developerId = developersClient.create(newDeveloper());
        Integer projectId = projectsClient.create(newProject());
        Integer result = projects_developersClient
                .addDeveloperToProjects_Developers(projectId,developerId);
        assertTrue(result == 1);
        Optional<Projects_Developers> projects_developers = projects_developersClient
                .findByIdFromProjects_Developers(projectId,developerId);

        assertTrue(projects_developers.isPresent());
    }

    @Test
    void shouldDeleteDeveloperFromProject_Developers() throws DatatypeConfigurationException {
        Integer developerId = developersClient.create(newDeveloper());
        Integer projectId = projectsClient.create(newProject());
        Integer result = projects_developersClient
                .addDeveloperToProjects_Developers(projectId,developerId);
        assertTrue(result == 1);
        Integer resultDelete = projects_developersClient
                .deleteDeveloperFromProject_Developers(projectId, developerId);
        assertTrue(resultDelete == 1);
    }

    @Test
    void shouldFindByIdFromProjects_Developers() throws DatatypeConfigurationException {
        Integer developerId = developersClient.create(newDeveloper());
        Integer projectId = projectsClient.create(newProject());
        Integer result = projects_developersClient
                .addDeveloperToProjects_Developers(projectId,developerId);
        assertTrue(result == 1);

        Projects_Developers projects_developers = projects_developersClient
                .findByIdFromProjects_Developers(projectId, developerId).get();
        System.out.println("___________________ = " + projects_developers);
    }

    private static Developers newDeveloper(){
        Developers developer = new Developers();
        String firstName = RandomStringUtils.randomAlphabetic(LASTNAME_SIZE);
        developer.setFirstName(firstName);
        String lastName = RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE);
        developer.setLastName(lastName);
        return developer;
    }

    private Projects newProject(){
        Projects project = new Projects();
        project.setDescription("Test");
        return project;
    }
}