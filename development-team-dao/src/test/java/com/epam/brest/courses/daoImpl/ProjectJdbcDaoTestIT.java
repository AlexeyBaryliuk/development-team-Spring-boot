package com.epam.brest.courses.daoImpl;

import com.epam.brest.courses.dao.ProjectsJdbcDao;
import com.epam.brest.courses.model.Projects;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml"})
class ProjectJdbcDaoTestIT {

    @Autowired
    Projects project;

    private ProjectsJdbcDao projectsDao;

    @Autowired
    public ProjectJdbcDaoTestIT(ProjectsJdbcDao projectsDao) {
        this.projectsDao = projectsDao;
    }


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
        Optional<Projects> projectOptional = projectsDao.findById(id);

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
        Integer id =projectsDao.create(projectTest);
        System.out.println(projectsDao.findById(id).get().getDateAdded());
        Optional<Projects> optionalProjects = projectsDao.findById(id);
            assertTrue(optionalProjects.isPresent());

        optionalProjects.get().setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));

        Integer result = projectsDao.update(optionalProjects.get());

        Optional<Projects> optionalUpdateProjects = projectsDao.findById(id);
            assertTrue(optionalUpdateProjects.isPresent());

        assertEquals(1, result.intValue());
        assertEquals( checkDate, optionalUpdateProjects.get().getDateAdded());
        assertSame(optionalProjects.get().getDescription(), optionalUpdateProjects.get().getDescription());
    }

    @Test
    void shouldCreateProject() {

        Projects projectTest = project;
        projectTest.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        LocalDate date = projectTest.getDateAdded();
        String description = projectTest.getDescription();
        Integer id = projectsDao.create(projectTest);
        assertEquals(description, projectsDao.findById(id).get().getDescription());

        assertEquals(date, projectsDao.findById(id).get().getDateAdded());
        System.out.println("DateAdded_________________________" + projectsDao.findById(id).get().getDateAdded());
        assertNotNull(id);
    }

    @Test
    void shouldDeleteProjectById() {

        Projects projectTest = project;
        projectTest.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer id = projectsDao.create(projectTest);
        assertTrue(projectsDao.findById(id).isPresent());
        projectsDao.delete(id);
        assertFalse(projectsDao.findById(id).isPresent());
    }

}