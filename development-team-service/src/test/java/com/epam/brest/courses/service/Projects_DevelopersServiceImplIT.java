package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.Projects_Developers;
import com.epam.brest.courses.service.testConfig.TestConfig;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes={Projects_Developers.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:create-db.sql", "classpath:insert-db.sql"})
@ActiveProfiles("test")
public class Projects_DevelopersServiceImplIT {

    private Developers developers = new Developers();

    private Projects project = new Projects();

    @Autowired
    private DevelopersServiceImpl developersService;

    @Autowired
    private ProjectsServiceImpl projectsService;

    @Autowired
    private Projects_DevelopersServiceImpl projects_developersService;

    @Test
    void shouldSelectDevelopersFromProjects_Developers() {

        developers.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        developers.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
        Integer firstDeveloperId = developersService.create(developers);
        Integer secondDeveloperId = developersService.create(developers);

        Projects newProject = project;
        newProject.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectsService.create(newProject);
        Integer firstResult = projects_developersService.addDeveloperToProjects_Developers(projectId, firstDeveloperId);
        Integer secondResult = projects_developersService.addDeveloperToProjects_Developers(projectId, secondDeveloperId);
        List<Developers> developersList = projects_developersService.selectDevelopersFromProjects_Developers(projectId);
        assertEquals(2, developersList.size());
        assertEquals(0, firstResult.intValue());
        assertEquals(0, secondResult.intValue());
    }

    @Test
    void shouldAddDeveloperToProjects_Developers() {

        developers.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        developers.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
        Integer developerId = developersService.create(developers);
        Projects newProject = project;
        newProject.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectsService.create(newProject);
        Integer result = projects_developersService.addDeveloperToProjects_Developers(projectId, developerId);

        List<Developers> developersList = projects_developersService.selectDevelopersFromProjects_Developers(projectId);
        assertEquals(1, developersList.size());
        assertEquals(0, result.intValue());
    }

    @Test
    void shouldDeleteDeveloperFromProject_Developers() {

        developers.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        developers.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
        Integer developerId = developersService.create(developers);
        Projects newProject = project;
        newProject.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectsService.create(newProject);

        Integer result = projects_developersService.addDeveloperToProjects_Developers(projectId, developerId);
        List<Developers> developersList = projects_developersService.selectDevelopersFromProjects_Developers(projectId);
        assertEquals(1, developersList.size());
        assertEquals(0, result.intValue());

        Integer resultAfterDelete = projects_developersService.deleteDeveloperFromProject_Developers(projectId, developerId);
        List<Developers> developersListAfterDelete = projects_developersService.selectDevelopersFromProjects_Developers(projectId);
        assertEquals(0, developersListAfterDelete.size());
        assertEquals(1, resultAfterDelete.intValue());
    }
}
