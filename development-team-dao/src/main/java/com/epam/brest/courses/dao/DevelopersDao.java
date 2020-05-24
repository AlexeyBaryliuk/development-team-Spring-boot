package com.epam.brest.courses.dao;


import com.epam.brest.courses.model.Developers;

import java.util.List;
import java.util.Optional;

/**
 * DevelopersDao interface.
 */
public interface DevelopersDao {

    /**
     * Find all developers.
     *
     * @return developers list.
     */
    List<Developers> findAll();

    /**
     * Find developer by Id.
     *
     * @param developerId developer's id.
     * @return developer.
     */
    Optional<Developers> findByDeveloperId(Integer developerId);

    /**
     * Persist new developer.
     *
     * @param developer developer.
     * @return persisted developer's id.
     */
    Integer create(Developers developer);

    /**
     * Update developer.
     *
     * @param developer developer.
     * @return number of updated records in the database.
     */
    Integer update (Developers developer);

    /**
     * Delete developer.
     *
     * @param developerId developer's id.
     * @return number of updated records in the database.
     */
    Integer deleteByDeveloperId(Integer developerId);

}

