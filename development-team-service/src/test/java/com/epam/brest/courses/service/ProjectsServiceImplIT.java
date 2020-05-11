package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Projects;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml"
                                    , "classpath*:test-service.xml"})
class ProjectsServiceImplIT {


    @Autowired
    Projects project;

    ProjectsServiceImpl projectsService;

    @Autowired
    public ProjectsServiceImplIT(ProjectsServiceImpl projectsService) {
        this.projectsService = projectsService;
    }

    @Test
    void shouldFindAll() {

        List<Projects> projects = projectsService.findAll();
        assertNotNull(projects);
        assertTrue(projects.size() > 0);
    }

    @Test
    void shouldFindById() {

        project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        String description = project.getDescription();
        LocalDate testDate = project.getDateAdded();
        Integer projectId = projectsService.create(project);

        Optional<Projects> optionalProjects = projectsService.findById(projectId);
        assertTrue(optionalProjects.isPresent());
        assertEquals(testDate,optionalProjects.get().getDateAdded());
        assertEquals(description,optionalProjects.get().getDescription());
    }

    @Test
    void shouldUpdate() {

        project.setDescription("TextBeforeUpdate");
        String description = project.getDescription();
        LocalDate testDate = project.getDateAdded();
        Integer projectId = projectsService.create(project);

        Optional<Projects> projectBeforeUpdate = projectsService.findById(projectId);
        assertTrue(projectBeforeUpdate.isPresent());
        projectBeforeUpdate.get().setDescription("TextAfterUpdate");

        projectsService.update(projectBeforeUpdate.get());
        Optional<Projects> projectAfterUpdate = projectsService.findById(projectId);
        assertTrue(projectAfterUpdate.isPresent());
        assertNotEquals(description,projectAfterUpdate.get().getDescription());
        assertEquals("TextAfterUpdate",projectAfterUpdate.get().getDescription());
        assertEquals(testDate,projectAfterUpdate.get().getDateAdded());
    }

    @Test
    void shouldCreate() {

        project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        String description = project.getDescription();
        Integer projectId = projectsService.create(project);

        Optional<Projects> optionalProjects = projectsService.findById(projectId);
        assertTrue(optionalProjects.isPresent());
        assertEquals(description,optionalProjects.get().getDescription());
    }

    @Test
    void shouldDelete() {

        project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        String description = project.getDescription();
        Integer projectId = projectsService.create(project);

        projectsService.delete(projectId);
        Optional<Projects> optionalProjects = projectsService.findById(projectId);
        assertFalse(optionalProjects.isPresent());


    }
}