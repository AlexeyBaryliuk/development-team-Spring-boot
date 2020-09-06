package com.epam.brest.courses.rest_app.controllers;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.rest_app.exception.ProjectsNotFoundException;
import com.epam.brest.courses.service.ProjectsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Collection;

/**
 * ProjectsController
 */
@Profile("rest")
@RestController
@RequestMapping("/projects")
public class ProjectsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsController.class);

    private final ProjectsService projectsService;

    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;

    }

    /**
     * Goto projects list page.
     *
     * @return view name
     */
    @GetMapping
    public final Collection<Projects> projects() {

        LOGGER.debug("projects()");
        return projectsService.findAll();
    }

    /**
     *Find project by id.
     *
     * @param projectId
     * @return Project.
     */
    @GetMapping(value = "/{projectId}")
    public Projects findByDeveloperId(@PathVariable Integer projectId) {

        LOGGER.debug("findById");
        return projectsService.findByProjectId(projectId).orElseThrow(() -> new ProjectsNotFoundException(projectId));
    }

    /**
     * Add project by description.
     * @param description
     * @return Project id.
     */
    @PostMapping(value = "/addByDescription", consumes = "application/json", produces = "application/json" )
    public Integer add(@RequestBody String description) throws DatatypeConfigurationException {

        Projects project = new Projects();
        LOGGER.debug("Add project {}", description);
        project.setDescription(description);
        return projectsService.create(project);
    }

    /**
     * Add project with requestBody = Projects project.
     * @param project
     * @returnnew Project().
     */
    @PostMapping(consumes = "application/json", produces = "application/json" )
    public Integer add(@RequestBody Projects project) throws DatatypeConfigurationException {

        LOGGER.debug("Add project {}", project);

        return projectsService.createF(project);
    }

    /**
     * Update project with request body = Projects project.
     *
     * @return new ResponseEntity.
     */
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> updateProject(@RequestBody Projects project) throws DatatypeConfigurationException {

        LOGGER.debug("updateProjectt({})", project);
        int result = projectsService.update(project);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     *Delete project by id.
     *
     * @param projectId
     * @return Affected rows.
     */
    @DeleteMapping(value = "/delete/{projectId}" )
    public Integer delete(@PathVariable Integer projectId){

        LOGGER.debug("delete()");
        return projectsService.delete(projectId);
    }

}
