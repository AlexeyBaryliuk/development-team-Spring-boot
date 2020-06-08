package com.epam.brest.courses.service;

import java.util.List;

public interface FakerService {

    /**
     * Change test data of projects.
     *
     * @param locale  Language for generating test data.
     * @param numberOfChanges The number of changes in test data.
     */
    void changeProjectsTestData(String locale, Integer numberOfChanges);

    /**
     * Change test data of developers.
     *
     * @param locale  Language for generating test data.
     * @param numberOfChanges The number of changes in test data.
     */
    void changeDevelopersTestsData(String locale, Integer numberOfChanges);

    /**
     * Find all locale for faker.
     *
     * @return All locale for faker.
     */
    List<String> findAllFakerLocale();

}
