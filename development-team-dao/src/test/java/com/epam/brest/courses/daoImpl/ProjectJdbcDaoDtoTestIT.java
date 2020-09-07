package com.epam.brest.courses.daoImpl;

import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.dao.ProjectsDaoDto;
import com.epam.brest.courses.daoImpl.config.TestConfig;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import org.apache.commons.lang.RandomStringUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={ProjectsDto.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class ProjectJdbcDaoDtoTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectJdbcDaoDtoTestIT.class);

    @Autowired
    private ProjectsDaoDto projectJdbcDaoDto;

    @Autowired
    private ProjectsDao projectJdbcDao;

    private Projects project = new Projects();

    @Test
    void shouldFindBetweenDates() throws ParseException {

        LocalDate dateStart = LocalDate.now();
        dateStart.minusDays(2);
        LocalDate dateEnd = LocalDate.now();
        dateEnd.plusDays(2);

        Projects projectStart = project;
        projectStart.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer idStart = projectJdbcDao.saveAndFlush(projectStart).getProjectId();
            assertTrue(idStart > 0);

        Projects projectEnd = project;
        projectEnd.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer idEnd = projectJdbcDao.saveAndFlush(projectEnd).getProjectId();
            assertTrue(idEnd > 0);

        List<ProjectsDto> projectsList = projectJdbcDaoDto.findAllByDateAddedBetween(dateStart,dateEnd);
            assertTrue(projectsList.size() > 0);

    }

    @Test
    void countOfDevelopers() {
        List<ProjectsDto> projectsDtoList = projectJdbcDaoDto.countOfDevelopers();
        assertTrue(projectsDtoList.size() > 0);
        assertTrue(projectsDtoList.get(1).getCountOfDevelopers() > 0);
        assertNotNull(projectsDtoList);
    }
}