package com.epam.brest.courses.exception;


/**
 * ProjectsNotFoundException.
 */
public class ProjectsNotFoundException extends RuntimeException {

    /**
     * Constructor with argument Integer id.
     */
    public ProjectsNotFoundException(Integer id) {
        super("Project not found for id: " + id);
    }
}
