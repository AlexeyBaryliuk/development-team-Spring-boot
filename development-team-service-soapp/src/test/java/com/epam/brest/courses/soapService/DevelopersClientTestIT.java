package com.epam.brest.courses.soapService;

import com.epam.brest.courses.configSoap.ProjectConfiguration;
import com.epam.brest.courses.model.Developers;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
class DevelopersClientTestIT {

    @Autowired
    private DevelopersClient developersClient;

    @Test
    void shouldFindAllDevelopers() {
        List<Developers> developersList = developersClient.findAll();

        assertTrue(developersList.size() > 0);
    }

    @Test
    void shouldFindDeveloperByDeveloperId() {

        Integer developerId = developersClient.create(newDeveloper());
        Developers developer = developersClient.findByDeveloperId(developerId).get();

        assertEquals(developer.getDeveloperId(), developerId);
    }

    @Test
    void create() {

        Integer developerId = developersClient.create(newDeveloper());
        Optional<Developers> developer = developersClient.findByDeveloperId(developerId);

        assertTrue(developer.isPresent());
    }

    @Test
    void shouldUpdateDeveloper() {

        Integer developerId = developersClient.create(newDeveloper());
        Developers developerBeforUpdate = developersClient.findByDeveloperId(developerId).get();

        String firstName = RandomStringUtils.randomAlphabetic(LASTNAME_SIZE);
        developerBeforUpdate.setFirstName(firstName);
        String lastName = RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE);
        developerBeforUpdate.setLastName(lastName);

        developersClient.update(developerBeforUpdate);
        Developers developerAfterUpdate = developersClient.findByDeveloperId(developerId).get();

        assertEquals(firstName, developerAfterUpdate.getFirstName());
        assertEquals(lastName, developerAfterUpdate.getLastName());
    }

    @Test
    void shouldDeleteDeveloperByDeveloperId() {

        Integer developerId = developersClient.create(newDeveloper());
        Integer resulteDelete = developersClient.deleteByDeveloperId(developerId);

        assertEquals(resulteDelete, 1);

    }

    private  Developers newDeveloper(){
        Developers developer = new Developers();
        String firstName = RandomStringUtils.randomAlphabetic(LASTNAME_SIZE);
        developer.setFirstName(firstName);
        String lastName = RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE);
        developer.setLastName(lastName);
        return developer;
    }
}