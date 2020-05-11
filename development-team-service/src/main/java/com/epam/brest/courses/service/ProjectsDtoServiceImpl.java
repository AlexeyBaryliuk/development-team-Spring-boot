package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.ProjectsJdbcDaoDto;
import com.epam.brest.courses.model.dto.ProjectsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectsDtoServiceImpl implements ProjectsDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsDtoServiceImpl.class);

    private final ProjectsJdbcDaoDto projectsJdbcDaoDto;

    public ProjectsDtoServiceImpl(ProjectsJdbcDaoDto projectsJdbcDaoDto) {
        this.projectsJdbcDaoDto = projectsJdbcDaoDto;
    }

    @Override
    public List<ProjectsDto> findBetweenDates(LocalDate dateStart, LocalDate dateEnd) {

        LOGGER.debug("Find project between dates - findBetweenDates");
        return projectsJdbcDaoDto.findBetweenDates(dateStart,dateEnd);
    }

    @Override
    public List<ProjectsDto> countOfDevelopers() {

        LOGGER.debug("Count of developers - countOfDevelopers()");

        return projectsJdbcDaoDto.countOfDevelopers();
    }
}
