package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;

import java.util.List;
import java.util.Optional;

/**
 * DevelopersService interface.
 */
public interface DevelopersService {
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
    Optional<Developers> findById(Integer developerId);

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
    Integer delete (Integer developerId);
}

