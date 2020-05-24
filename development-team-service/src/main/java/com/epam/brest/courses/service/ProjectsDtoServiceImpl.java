package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.ProjectsDaoDto;
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

    private final ProjectsDaoDto ProjectsDaoDto;

    public ProjectsDtoServiceImpl(ProjectsDaoDto ProjectsDaoDto) {
        this.ProjectsDaoDto = ProjectsDaoDto;
    }

    @Override
    public List<ProjectsDto> findAllByDateAddedBetween(LocalDate dateStart, LocalDate dateEnd) {

        LOGGER.debug("Find project between dates - findBetweenDates");
        return ProjectsDaoDto.findAllByDateAddedBetween(dateStart,dateEnd);
    }

    @Override
    public List<ProjectsDto> countOfDevelopers() {

        LOGGER.debug("Count of developers - countOfDevelopers()");
        return ProjectsDaoDto.countOfDevelopers();
    }

}
