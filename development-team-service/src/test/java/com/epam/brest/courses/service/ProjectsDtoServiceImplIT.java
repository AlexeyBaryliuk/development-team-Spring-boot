package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.testConfig.TestConfig;
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

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={ProjectsDto.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class ProjectsDtoServiceImplIT {

    @Autowired
    private ProjectsServiceImpl projectsService;

    @Autowired
    private ProjectsDtoServiceImpl projectsDtoService;

    private Projects project = new Projects();


    @Test
    void shouldFindBetweenDates() {

        LocalDate dateStart = LocalDate.now();
        dateStart.minusDays(2);
        LocalDate dateEnd = LocalDate.now();
        dateEnd.plusDays(2);

        Projects projectStart = project;
        projectStart.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer idStart = projectsService.create(projectStart);
        assertTrue(idStart > 0);

        Projects projectEnd = project;
        projectEnd.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer idEnd = projectsService.create(projectEnd);
        assertTrue(idEnd > 0);

        List<ProjectsDto> projectsList = projectsDtoService.findAllByDateAddedBetween(dateStart,dateEnd);
        assertTrue(projectsList.size() > 0);

    }

    @Test
    void shouldFindCountOfDevelopers() {

        List<ProjectsDto> projectsDtoList = projectsDtoService.countOfDevelopers();
        assertTrue(projectsDtoList.size() > 0);
        assertTrue(projectsDtoList.get(1).getCountOfDevelopers() > 0);
        assertNotNull(projectsDtoList);
    }
}