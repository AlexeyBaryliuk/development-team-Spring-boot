package com.epam.brest.courses.daoImpl;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.daoImpl.config.TestConfig;
import com.epam.brest.courses.model.Developers;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes={Developers.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class DevelopersDaoTestIT {

    @Autowired
    private DevelopersDao developersJdbcDao;

    @Test
    void shouldFindAllDevelopers() {

        List<Developers> developersList = developersJdbcDao.findAll();
        assertTrue(developersList.size() > 0);
    }

    @Test
    void shouldFindDeveloperById() {

       Developers developer =newDeveloper();

        Integer developerId = developersJdbcDao.create(developer);
        Optional<Developers> optionalDeveloper = developersJdbcDao.findByDeveloperId(developerId);

        assertTrue(optionalDeveloper.isPresent());
        assertEquals(optionalDeveloper.get().getDeveloperId(), developerId);
        assertEquals(developer.getFirstName(),optionalDeveloper.get().getFirstName());
        assertEquals(developer.getLastName(),optionalDeveloper.get().getLastName());
    }

    @Test
    void shouldCreateDeveloper() {

        Developers developer = newDeveloper();
        Integer id = developersJdbcDao.create(developer);
        assertTrue(id > 0);

        Optional<Developers> optionalDevelopers = developersJdbcDao.findByDeveloperId(id);
        assertEquals(developer.getLastName(),optionalDevelopers.get().getLastName());
        assertEquals(developer.getFirstName(), optionalDevelopers.get().getFirstName());
    }

    @Test
    void shouldUpdateDeveloper() {

        Developers developer = newDeveloper();
        String beforeUpdateLastName = developer.getLastName();
        String beforeUpdateFirstName = developer.getFirstName();

        Integer id = developersJdbcDao.create(developer);
        Optional<Developers> optionalDevelopers = developersJdbcDao.findByDeveloperId(id);

            String afterUpdateLastName = "TestLastName";
            optionalDevelopers.get().setLastName(afterUpdateLastName);
                String afterUpdateFirstName = "TestFirstName";
                optionalDevelopers.get().setFirstName(afterUpdateFirstName);

        Integer result = developersJdbcDao.update(optionalDevelopers.get());

        assertTrue(result == 1);
        assertNotEquals(beforeUpdateLastName, afterUpdateLastName);
        assertNotEquals(beforeUpdateFirstName, afterUpdateFirstName);
    }

    @Test
    void shouldDeleteDeveloper() {

        Developers developer = newDeveloper();
        Integer id = developersJdbcDao.create(developer);
        Integer result = developersJdbcDao.deleteByDeveloperId(id);

        assertEquals(1, result.intValue());
        Optional<Developers> optionalDevelopers = developersJdbcDao.findByDeveloperId(id);
        assertFalse(optionalDevelopers.isPresent());

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