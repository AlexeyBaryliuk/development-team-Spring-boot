package com.epam.brest.courses.rest_app.controllers;

import com.epam.brest.courses.service.FakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProjectsController
 */
@RestController
@RequestMapping("/changeTestData")
public class FakeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeController.class);

    private final FakerService fakerService;

    public FakeController(FakerService fakerService) {
        this.fakerService = fakerService;
    }

    @GetMapping(value = "/developers")
    public String changeDevelopersTestData(@RequestParam(value = "locale", required = false) String locale,
                                           @RequestParam(value = "number", required = false) Integer numOfChanges) {

        LOGGER.debug("changeDevelopersTestData()");
        fakerService.changeDevelopersTestsData(locale, numOfChanges);
        return "Developers test data has been changed.";
    }

    @GetMapping(value = "/projects" )
    public String changeProjectsTestData(@RequestParam(value = "locale", required = false) String locale,
                                         @RequestParam(value = "number", required = false) Integer numOfChanges) {

        LOGGER.debug("changeProjectsTestData()");
        fakerService.changeProjectsTestData(locale, numOfChanges);
        return "Projects test data has been changed.";
    }

    @GetMapping
    public List<String> findAllLocaleForFaker(){

        LOGGER.debug("findAllLocaleForFaker()");

        return fakerService.findAllFakerLocale();
    }
}
