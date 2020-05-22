package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.dto.ProjectsDto;

import java.time.LocalDate;
import java.util.List;

/**
 * ProjectsDaoDto interface.
 */
public interface ProjectsDaoDto {

    /**
     * Find all projects between two dates.
     *
     * @param dateStart
     * @param dateEnd
     * @return project's list.
     */
    List<ProjectsDto> findAllByDateAddedBetween(LocalDate dateStart, LocalDate dateEnd);

    /**
     * Count of developers
     *
     * @return project's list.
     */
    List<ProjectsDto> countOfDevelopers();
}

