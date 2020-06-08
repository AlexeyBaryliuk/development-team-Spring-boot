package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.testConfig.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={Developers.class, Projects.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
class FakerServiceImplIT {

    @Autowired
    private FakerService fakerService;

    @Autowired
    private DevelopersDao developersDao;

    @Autowired
    private ProjectsDao projectsDao;

    @Test
    void shouldChangeDevelopersData() {

        Integer numOfChanges = 2;
        Integer countBeforeChanges = developersDao.findAll().size();
        fakerService.changeDevelopersTestsData("en", numOfChanges);
        Integer countAfterChanges = developersDao.countOfRow();

        assertEquals(countAfterChanges, countBeforeChanges*numOfChanges);

    }

    @Test
    void shouldChangeProjectsData() {

        Integer numOfChanges = 2;
        Integer countBeforeChanges = projectsDao.findAll().size();

        fakerService.changeProjectsTestData("en", numOfChanges);

        Integer countAfterChanges = projectsDao.countOfRow();

        assertEquals(countAfterChanges, countBeforeChanges*numOfChanges);


    }
    @Test
    void projectsDataShouldBeChangedWithoutParameters() {

        Integer countBeforChenges = projectsDao.findAll().size();

        fakerService.changeProjectsTestData(null, null);

        Integer countAfterChanges = projectsDao.countOfRow();

        assertEquals(countAfterChanges, countBeforChenges);

    }

    @Test
    void developersDataShouldBeChangedWithoutParameters() {

        Integer countBeforChenges = developersDao.findAll().size();

        fakerService.changeDevelopersTestsData(null, null);

        Integer countAfterChanges = developersDao.countOfRow();

        assertEquals(countAfterChanges, countBeforChenges);

    }

    @Test
    void shouldFindAllLocaleForFaker(){

        List<String> localeList = fakerService.findAllFakerLocale();

        assertTrue(localeList.size() > 0);
        }

}