package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Projects;
import java.util.List;
import java.util.Optional;

/**
 * ProjectsDao interface.
 */
public interface ProjectsDao {

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
    Integer deleteByProjectId(Integer projectId);

    /**
     * Select count of row.
     *
     * @return Count Of row.
     */

    Integer countOfRow();

    /**
     * Delete projects.
     *
     * @return number of updated records in the database.
     */
    Integer deleteAllProjects();
}
