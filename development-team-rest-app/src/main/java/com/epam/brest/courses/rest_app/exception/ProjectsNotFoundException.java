package com.epam.brest.courses.rest_app.exception;


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
