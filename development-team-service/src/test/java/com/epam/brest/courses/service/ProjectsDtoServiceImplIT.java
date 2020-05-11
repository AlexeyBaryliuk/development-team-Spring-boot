package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:test-service.xml"})
class ProjectsDtoServiceImplIT {

    @Autowired
    ProjectsServiceImpl projectsService;


    ProjectsDtoServiceImpl projectsDtoService;

    @Autowired
    public ProjectsDtoServiceImplIT(ProjectsDtoServiceImpl projectsDtoService) {
        this.projectsDtoService = projectsDtoService;
    }

    @Autowired
    Projects project;


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

        List<ProjectsDto> projectsList = projectsDtoService.findBetweenDates(dateStart,dateEnd);
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