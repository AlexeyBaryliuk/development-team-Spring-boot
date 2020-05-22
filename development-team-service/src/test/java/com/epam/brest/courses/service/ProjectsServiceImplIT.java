package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={ProjectsDto.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class ProjectsServiceImplIT {

    private Projects project = new Projects();

    private ProjectsServiceImpl projectsService;

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
    void shouldfindByDeveloperId() {

        project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        String description = project.getDescription();
        LocalDate testDate = project.getDateAdded();
        Integer projectId = projectsService.save(project);

        Optional<Projects> optionalProjects = projectsService.findByDeveloperId(projectId);
        assertTrue(optionalProjects.isPresent());
        assertEquals(testDate,optionalProjects.get().getDateAdded());
        assertEquals(description,optionalProjects.get().getDescription());
    }

    @Test
    void shouldUpdate() {

        project.setDescription("TextBeforeUpdate");
        String description = project.getDescription();
        LocalDate testDate = project.getDateAdded();
        Integer projectId = projectsService.save(project);

        Optional<Projects> projectBeforeUpdate = projectsService.findByDeveloperId(projectId);
        assertTrue(projectBeforeUpdate.isPresent());
        projectBeforeUpdate.get().setDescription("TextAfterUpdate");

        projectsService.update(projectBeforeUpdate.get());
        Optional<Projects> projectAfterUpdate = projectsService.findByDeveloperId(projectId);
        assertTrue(projectAfterUpdate.isPresent());
        assertNotEquals(description,projectAfterUpdate.get().getDescription());
        assertEquals("TextAfterUpdate",projectAfterUpdate.get().getDescription());
        assertEquals(testDate,projectAfterUpdate.get().getDateAdded());
    }

    @Test
    void shouldsave() {

        project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        String description = project.getDescription();
        Integer projectId = projectsService.save(project);

        Optional<Projects> optionalProjects = projectsService.findByDeveloperId(projectId);
        assertTrue(optionalProjects.isPresent());
        assertEquals(description,optionalProjects.get().getDescription());
    }

    @Test
    void shouldDelete() {

        project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        String description = project.getDescription();
        Integer projectId = projectsService.save(project);

        projectsService.delete(projectId);
        Optional<Projects> optionalProjects = projectsService.findByDeveloperId(projectId);
        assertFalse(optionalProjects.isPresent());


    }
}