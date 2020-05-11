package com.epam.brest.courses.web_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Hello MVC controller.
 */
@Controller
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping(value = "/hello")
    public String hello( @RequestParam(value = "name", required = false, defaultValue = "world") String names,
                        Model model) {

        LOGGER.debug("hello(name:{}, model = {})", names, model);
        model.addAttribute("name", names);

        return "hello";
    }

}
