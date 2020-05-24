package com.epam.brest.courses.daoImpl;

import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.daoImpl.config.TestConfig;
import com.epam.brest.courses.model.Projects;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={Projects.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class ProjectJdbcDaoTestIT {

    @Autowired
    private ProjectsDao projectsDao;

    private Projects project = new Projects();

    @Test
    void shouldGetAllProjects() {

        List<Projects> projects = projectsDao.findAll();
       assertNotNull(projects);
       assertTrue(projects.size() > 0);
    }

    @Test
    void shouldGetProjectById() {

        Projects projectTest = project;
        projectTest.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer id = projectsDao.create(projectTest);
        Optional<Projects> projectOptional = projectsDao.findByProjectId(id);

        assertTrue(projectOptional.isPresent());
        assertNotNull(projectOptional.get().getDescription());
        assertEquals(projectOptional.get().getProjectId(), id);
        assertEquals(projectOptional.get().getDescription(), project.getDescription());
        assertNotNull(projectOptional.get().getDateAdded());
    }

    @Test
    void shouldUpdateProject() {

        Projects projectTest = project;
        projectTest.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        LocalDate checkDate = projectTest.getDateAdded();

        Integer id = projectsDao.create(projectTest);
        Optional<Projects> optionalProjects = projectsDao.findByProjectId(id);
            assertTrue(optionalProjects.isPresent());

        optionalProjects.get().setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));

        Integer result = projectsDao.update(optionalProjects.get());

        Optional<Projects> optionalUpdateProjects = projectsDao.findByProjectId(id);
            assertTrue(optionalUpdateProjects.isPresent());

        assertEquals(1, result.intValue());
        assertEquals( checkDate, optionalUpdateProjects.get().getDateAdded());
        assertEquals(optionalProjects.get().getDescription(), optionalUpdateProjects.get().getDescription());
    }

    @Test
    void shouldCreateProject() {

        Projects projectTest = project;
        projectTest.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        LocalDate date = projectTest.getDateAdded();
        String description = projectTest.getDescription();
        Integer id = projectsDao.create(projectTest);
        assertEquals(description, projectsDao.findByProjectId(id).get().getDescription());

        assertEquals(date, projectsDao.findByProjectId(id).get().getDateAdded());
        System.out.println("DateAdded_________________________" + projectsDao.findByProjectId(id).get().getDateAdded());
        assertNotNull(id);
    }

    @Test
    void shouldDeleteProjectById() {

        Projects projectTest = project;
        projectTest.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer id = projectsDao.create(projectTest);
        assertTrue(projectsDao.findByProjectId(id).isPresent());
        projectsDao.deleteByProjectId(id);
        assertFalse(projectsDao.findByProjectId(id).isPresent());
    }

}