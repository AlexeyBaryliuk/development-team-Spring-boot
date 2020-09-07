package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Projects;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;
import java.util.Optional;

/**
 * ProjectsService interface.
 */
public interface ProjectsService {

    /**
     * Find all projects.
     *
     * @return projects list.
     */
    List<Projects> findAll();

    /**
     * Find project by Id.
     *
     * @param projectId project's id.
     * @return project.
     */
    Optional<Projects> findByProjectId(Integer projectId);

    /**
     * Update project.
     *
     * @param project project.
     * @return number of updated records in the database.
     */
    Integer update(Projects project) throws DatatypeConfigurationException;

    /**
     * Persist new project.
     *
     * @param project project.
     * @return persisted project's id.
     */
    Integer create(Projects project) throws DatatypeConfigurationException;
    /**
     * Delete project.
     *
     * @param projectId project's id.
     * @return number of updated records in the database.
     */
    Integer delete(Integer projectId);
}
