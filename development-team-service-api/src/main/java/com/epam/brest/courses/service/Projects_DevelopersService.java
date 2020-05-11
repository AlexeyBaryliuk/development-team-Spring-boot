package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;

import java.util.List;
import java.util.Optional;

/**
 * Projects_DevelopersService interface.
 */
public interface Projects_DevelopersService {
    /**
     * Select all developers from Projects_Developers by projectId.
     *
     * @return developer's list.
     */
    List<Developers> selectDevelopersFromProjects_Developers(Integer projectId);

    /**
     * Persist developer to projects_developers.
     *
     * @param projectId, developerId
     * @return number of updated records in the database.
     */
    Integer addDeveloperToProjects_Developers(Integer projectId, Integer developerId);

    /**
     * Delete developer from Projects_Developers.
     *
     * @param developerId
     * @return number of updated records in the database.
     */
    Integer deleteDeveloperFromProject_Developers(Integer projectId, Integer developerId);

    /**
     *Find developer By Id from projects_developers.
     *
     * @param projectId
     * @param developerId
     * @return developer
     */

    Optional<Projects_Developers> findByIdFromProjects_Developers(Integer projectId, Integer developerId);
}
