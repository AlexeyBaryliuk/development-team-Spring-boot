package com.epam.brest.courses.daoImpl;


import com.epam.brest.courses.daoImpl.config.TestConfig;
import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.Projects_Developers;
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
import java.util.Optional;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={Projects_Developers.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
@ActiveProfiles("mySql")
public class Projects_DevelopersJdbcDaoTestIT {

    @Autowired
    private DevelopersJdbcDaoImpl developersJdbcDao;

    @Autowired
    private ProjectJdbcDaoImpl projectJdbcDao;

    @Autowired
    private Projects_DevelopersJdbcDaoImpl projects_developersJdbcDao;

    private Projects project = new Projects();

    @Test
    void shouldSelectDevelopersFromProjects_Developers() {

        Developers developer = newDeveloper();
        Integer firstDeveloperId = developersJdbcDao.create(developer);
        Integer secondDeveloperId = developersJdbcDao.create(developer);
        Projects newProject = project;
        newProject.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectJdbcDao.create(newProject);
        Integer resultFirst = projects_developersJdbcDao.addDeveloperToProjects_Developers(projectId, firstDeveloperId);
        Integer resultSecond = projects_developersJdbcDao.addDeveloperToProjects_Developers(projectId, secondDeveloperId);

        List<Developers> developersList = projects_developersJdbcDao.selectDevelopersFromProjects_Developers(projectId);
        assertEquals(2, developersList.size());
        assertEquals(0, resultFirst.intValue());
        assertEquals(0, resultSecond.intValue());

    }

    @Test
    void shoulAddDeveloperToProjects_Developers() {

        Developers developer = newDeveloper();
        Integer developerId = developersJdbcDao.create(developer);
        Projects newProject = project;
        newProject.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectJdbcDao.create(newProject);
        Integer result = projects_developersJdbcDao.addDeveloperToProjects_Developers(projectId, developerId);

        List<Developers> developersList = projects_developersJdbcDao.selectDevelopersFromProjects_Developers(projectId);
        assertEquals(1, developersList.size());
        assertEquals(0, result.intValue());
    }

    @Test
    void shoulDeleteDeveloperFromProject_Developers() {

        Developers developer = newDeveloper();
        Integer developerId = developersJdbcDao.create(developer);
        Projects newProject = project;
        newProject.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectJdbcDao.create(newProject);
        Integer resultFirst = projects_developersJdbcDao.addDeveloperToProjects_Developers(projectId, developerId);

        List<Developers> developersList = projects_developersJdbcDao.selectDevelopersFromProjects_Developers(projectId);
        assertEquals(1, developersList.size());
        assertEquals(0, resultFirst.intValue());

        Integer resultAfterDelete = projects_developersJdbcDao.deleteDeveloperFromProject_Developers(projectId, developerId);
        List<Developers> developersListAfterDelete = projects_developersJdbcDao.selectDevelopersFromProjects_Developers(projectId);
        assertEquals(0, developersListAfterDelete.size());
        assertEquals(1, resultAfterDelete.intValue());
    }

    @Test
    void shouldFindByIdFromProjects_Developers(){

        Developers developer = newDeveloper();
        Integer developerId = developersJdbcDao.create(developer);
        Projects newProject = project;
        newProject.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectJdbcDao.create(newProject);
        projects_developersJdbcDao.addDeveloperToProjects_Developers(projectId,developerId);
        Optional<Projects_Developers> listFromProjects_Developers = projects_developersJdbcDao
                .findByIdFromProjects_Developers(projectId,developerId);

        assertTrue(listFromProjects_Developers.isPresent());
//        assertEquals(projectId, developerFromProjects_Developers.get().getDeveloperId());

    }

    private static Developers newDeveloper(){
        Developers developer = new Developers();
        String firstName = RandomStringUtils.randomAlphabetic(LASTNAME_SIZE);
        developer.setFirstName(firstName);
        String lastName = RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE);
        developer.setLastName(lastName);
        return developer;
    }
}
