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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={Developers.class, Projects.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
class FakerServiceImplTest {

    @Autowired
    private FakerService fakerService;

    @Autowired
    private DevelopersDao developersDao;

    @Autowired
    private ProjectsDao projectsDao;

    @Test
    void shouldChangeDevelopersData() {

        Integer numOfChenges = 2;
        Integer countBeforChenges = developersDao.findAll().size();
        fakerService.changeData("en", numOfChenges);
        Integer countAfterChanges = developersDao.countOfRow();

        assertEquals(countAfterChanges, countBeforChenges*numOfChenges);

    }

    @Test
    void shouldChangeProjectsData() {

        Integer numOfChenges = 2;
        Integer countBeforChenges = projectsDao.findAll().size();

        fakerService.changeData("en", numOfChenges);

        Integer countAfterChanges = projectsDao.countOfRow();

        assertEquals(countAfterChanges, countBeforChenges*numOfChenges);


    }
}