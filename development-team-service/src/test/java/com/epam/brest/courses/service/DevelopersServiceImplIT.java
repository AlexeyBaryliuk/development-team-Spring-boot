package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml"
        , "classpath*:test-dao.xml"
        , "classpath*:test-service.xml"})
class DevelopersServiceImplIT {

    DevelopersServiceImpl developersService;

    @Autowired
    public DevelopersServiceImplIT(DevelopersServiceImpl developersService) {
        this.developersService = developersService;
    }

    @Autowired
    Developers developers;

    @Autowired
    Projects project;

    @Autowired
    ProjectsService projectsService;

    @Test
    void shouldFindAll() {

        List<Developers> developersList = developersService.findAll();
        assertNotNull(developersList);
        assertTrue(developersList.size() > 0);
    }

    @Test
    void shouldFindById() {

        developers.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        String lastName = developers.getLastName();
            developers.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
            String firstName = developers.getFirstName();

        Integer developerId = developersService.create(developers);

        Optional<Developers> optionalDevelopers = developersService.findById(developerId);
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

        Optional<Developers> optionalDevelopers = developersService.findById(developerId);
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
        Optional<Developers> optionalDevelopers = developersService.findById(developerId);

        optionalDevelopers.get().setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        optionalDevelopers.get().setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));

        int result = developersService.update(optionalDevelopers.get());
            assertEquals(1, result);
        Optional<Developers> optionalDevelopersAfterUpdate = developersService.findById(developerId);

            assertTrue(optionalDevelopersAfterUpdate.isPresent());
            assertNotEquals(lastName,optionalDevelopersAfterUpdate.get().getLastName());
            assertNotEquals(firstName,optionalDevelopersAfterUpdate.get().getFirstName());
    }

    @Test
    void shouldDelete() {

        developers.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        developers.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));

        Integer developerId = developersService.create(developers);

        int result = developersService.delete(developerId);
        assertEquals(1, result);

        Optional<Developers> optionalDevelopers = developersService.findById(developerId);
        assertFalse(optionalDevelopers.isPresent());
    }


}