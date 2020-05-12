package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class Projects_DevelopersServiceImplIT {

    @Autowired
    Developers developers;

    @Autowired
    Projects project;

    @Autowired
    DevelopersService developersService;

    @Autowired
    ProjectsService projectsService;

    @Autowired
    Projects_DevelopersService projects_developersService;

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
