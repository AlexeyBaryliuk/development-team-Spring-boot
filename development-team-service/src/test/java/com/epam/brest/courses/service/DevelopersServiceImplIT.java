package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.service.testConfig.TestConfig;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={Developers.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
class DevelopersServiceImplIT {

    @Autowired
    private DevelopersServiceImpl developersService;

    private Developers developers = new Developers();

    @Test
    void shouldFindAll() {

        List<Developers> developersList = developersService.findAll();
        assertNotNull(developersList);
        assertTrue(developersList.size() > 0);
    }

    @Test
    void shouldfindByDeveloperId() {

        developers.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        String lastName = developers.getLastName();
            developers.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
            String firstName = developers.getFirstName();

        Integer developerId = developersService.create(developers);

        Optional<Developers> optionalDevelopers = developersService.findByDeveloperId(developerId);
        assertTrue(optionalDevelopers.isPresent());
        assertEquals(lastName,optionalDevelopers.get().getLastName());
        assertEquals(firstName,optionalDevelopers.get().getFirstName());
    }

    @Test
    void shouldCreate() {

        developers.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        String lastName = developers.getLastName();
        developers.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
        String firstName = developers.getFirstName();

        Integer developerId = developersService.create(developers);

        Optional<Developers> optionalDevelopers = developersService.findByDeveloperId(developerId);
        assertTrue(optionalDevelopers.isPresent());
        assertEquals(lastName,optionalDevelopers.get().getLastName());
        assertEquals(firstName,optionalDevelopers.get().getFirstName());
    }

    @Test
    void shouldUpdate() {

        developers.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        String lastName = developers.getLastName();
            developers.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
            String firstName = developers.getLastName();

        Integer developerId = developersService.create(developers);
        Optional<Developers> optionalDevelopers = developersService.findByDeveloperId(developerId);

        optionalDevelopers.get().setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        optionalDevelopers.get().setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));

        int result = developersService.update(optionalDevelopers.get());
            assertEquals(1, result);
        Optional<Developers> optionalDevelopersAfterUpdate = developersService.findByDeveloperId(developerId);

            assertTrue(optionalDevelopersAfterUpdate.isPresent());
            assertNotEquals(lastName,optionalDevelopersAfterUpdate.get().getLastName());
            assertNotEquals(firstName,optionalDevelopersAfterUpdate.get().getFirstName());
    }

    @Test
    void shouldDelete() {

        developers.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        developers.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));

        Integer developerId = developersService.create(developers);

        int result = developersService.deleteByDeveloperId(developerId);
        assertEquals(1, result);

        Optional<Developers> optionalDevelopers = developersService.findByDeveloperId(developerId);
        assertFalse(optionalDevelopers.isPresent());
    }

    @Test
    void shouldDeleteAllDevelopers() {
        List<Developers> developersList = developersService.findAll();
        Integer countOfRow =  developersService.deleteAllDevelopers();

        assertEquals(developersList.size(),countOfRow);
    }

}