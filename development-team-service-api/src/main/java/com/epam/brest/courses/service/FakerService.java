package com.epam.brest.courses.service;

public interface FakerService {

    /**
     * Change test data.
     *
     * @param local  Language for generating test data.
     * @param numberOfChanges The number of changes in test data.
     */
    void changeData(String local, Integer numberOfChanges);

}
