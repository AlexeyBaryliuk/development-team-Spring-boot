package com.epam.brest.courses.web_app.controllers;

import com.epam.brest.courses.service.FakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/changeTestData")
public class FakerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakerController.class);

    @Autowired
    private final FakerService fakerService;

    public FakerController(FakerService fakerService) {
        this.fakerService = fakerService;
    }

    @GetMapping
    public String gotoChangeDataPage(Model model) {

        List<String> localeList = new ArrayList<>();
        localeList.add("en");
        localeList.add("ru");
        localeList.add("bb");
        model.addAttribute("locationList", localeList);
//        LOGGER.debug("changeProjectsTestData()");
//        fakerService.changeProjectsTestData(locale, numOfChanges);
        return "fakeData";
    }
}
