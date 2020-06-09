package com.epam.brest.courses.web_app.controllers;

import com.epam.brest.courses.service.FakerService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/changeTestData")
@SuppressFBWarnings(value = "DLS_DEAD_LOCAL_STORE")
public class FakerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakerController.class);

    @Autowired
    private final FakerService fakerService;

    public FakerController(FakerService fakerService) {
        this.fakerService = fakerService;
    }

    @GetMapping
    public String gotoChangeDataPage(Model model) {

        List<String> localeList;
        localeList = fakerService.findAllFakerLocale();

        model.addAttribute("locationList",localeList);

        return "fakeData";
    }

    @GetMapping(value = "/developers" )
    public String changeDevelopersTestData(@RequestParam(value = "locale", required = false) String locale,
                                           @RequestParam(value = "number", required = false) Integer numOfChanges) {

        LOGGER.debug("changeDevelopersTestData()");
        fakerService.changeDevelopersTestsData(locale, numOfChanges);

        return "redirect:/changeTestData";
    }

    @GetMapping(value = "/projects" )
    public String changeProjectsTestData(@RequestParam(value = "locale", required = false) String locale,
                                         @RequestParam(value = "number", required = false) Integer numOfChanges) {

        LOGGER.debug("changeProjectsTestData()");
        fakerService.changeProjectsTestData(locale, numOfChanges);

        return "redirect:/changeTestData";
    }

}
