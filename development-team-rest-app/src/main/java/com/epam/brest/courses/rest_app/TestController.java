package com.epam.brest.courses.rest_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Root controller.
 */
@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private static final String TEST_CONTROLLER = "testController for TestController class";

    @GetMapping(value = "/testController")
    public String testController(){

        LOGGER.debug("Controller's test");
        return TEST_CONTROLLER;
    }
}
