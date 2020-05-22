package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Projects;
import java.util.List;
import java.util.Optional;

/**
 * ProjectsJdbcDao interface.
 */
public interface ProjectsJdbcDao {

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
    Optional<Projects> findByDeveloperId(Integer projectId);

    /**
     * Update project.
     *
     * @param project project.
     * @return number of updated records in the database.
     */
    Integer update(Projects project);

    /**
     * Persist new project.
     *
     * @param project project.
     * @return persisted project's id.
     */
    Integer create(Projects project);

    /**
     * Delete project.
     *
     * @param projectId project's id.
     * @return number of updated records in the database.
     */
    Integer delete(Integer projectId);
}
