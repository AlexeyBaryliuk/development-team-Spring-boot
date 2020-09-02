package com.epam.brest.courses.rest_app.controllers;


import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;


/**
 * ProjectsDtoController
 */
@Profile("rest")
@RestController
@RequestMapping("/projectsDto")
public class ProjectsDtoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsDtoController.class);

   private final ProjectsDtoService projectsDtoService;

    public ProjectsDtoController(ProjectsDtoService projectsDtoService) {
        this.projectsDtoService = projectsDtoService;
    }

    /**
     * Find projects Dto between two dates.
     *
     * @param dateStart
     * @param dateEnd
     * @return Collection of projects Dto between two dates.
     */
    @GetMapping
    public Collection<ProjectsDto> findBetweenDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart
                                                   ,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd) {

        LOGGER.debug("findBetwenDates({},{})", dateStart, dateEnd);
        return projectsDtoService.findAllByDateAddedBetween(dateStart, dateEnd);

    }

    /**
     *Find all projects Dto.
     *
     * @return List of all projects Dto.
     */
    @GetMapping("/findAll")
    public Collection<ProjectsDto> findAll() {

        LOGGER.debug("findAll()");
        return projectsDtoService.countOfDevelopers();

    }
}