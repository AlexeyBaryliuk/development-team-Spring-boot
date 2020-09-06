package com.epam.brest.courses.service;

import com.epam.brest.courses.model.dto.ProjectsDto;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.util.List;

/**
 * ProjectsService interface.
 */
public interface ProjectsDtoService {

    /**
     * Find all projects between two dates.
     *
     * @param dateStart
     * @param dateEnd
     * @return project's list.
     */
    List<ProjectsDto> findAllByDateAddedBetween(LocalDate dateStart, LocalDate dateEnd) throws DatatypeConfigurationException;

    /**
     * Count of developers
     *
     * @return project's list.
     */
    List<ProjectsDto> countOfDevelopers() throws DatatypeConfigurationException;
}
