package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.web_app.validators.DevelopersValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/developers")
public class DevelopersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersController.class);

    @Autowired
    private DevelopersValidator developersValidator;

    Developers developer = new Developers();

    private final DevelopersService developersService;

    public DevelopersController(DevelopersService developersService) {
        this.developersService = developersService;
    }

    @GetMapping
    public String findAll(Model model){

        LOGGER.debug("DCONTROLLER findAll()");
        List<Developers> developersList = developersService.findAll();
        model.addAttribute("developers", developersList);
        return "developers";
    }

    @GetMapping("/{developerId}")
    public String gotoEditDevelopersPage(@PathVariable Integer developerId, Model model) {

        LOGGER.debug("DCONTROLLER - gotoEditDevelopersPage");
        Optional<Developers> optionalDevelopers = developersService.findById(developerId);

        if (optionalDevelopers.isPresent()) {
            model.addAttribute("isNew", false);
            model.addAttribute("developer", optionalDevelopers.get());
            model.addAttribute("developers", developersService.findAll());
            return "developer";
        } else {
            // TODO department not found - pass error message as parameter or handle not found error
            return "redirect:/developers";
        }
    }

    /**
     * Update developer.
     *
     * @param developer developer with filled data.
     * @param result     binding result
     * @return view name
     */
    @PostMapping(value = "/{Id}")
    public String updateDeveloper(@ModelAttribute("developer")
                                 @Valid Developers developer
                                 , BindingResult result){

        LOGGER.debug("updateDeveloper({}, {})", developer, result);
        developersValidator.validate(developer, result);
        if (result.hasErrors()) {
            return "developer";
        } else {
            this.developersService.update(developer);
            return "redirect:/developers";
        }
    }

    @GetMapping("/developer")
    public String gotoAddDeveloper(Model model){

        LOGGER.debug("DCONTROLLER - gotoAddDeveloper()");
        model.addAttribute("isNew", true);
        model.addAttribute("developer", developer);
        return "developer";
    }

    /**
     * Persist new employee into persistence storage.
     *
     * @param developer new developer with filled data.
     * @param result     binding result.
     * @return view name
     */
    @PostMapping(value = "/developer")
    public String addDeveloper(@ModelAttribute("developer") @Valid Developers developer,
                              BindingResult result) {

        LOGGER.debug("addEmployee({}, {})", developer, result);

        developersValidator.validate(developer, result);
        if (result.hasErrors()) {
            return "developer";
        } else {
            this.developersService.create(developer);
            return "redirect:/developers";
        }
    }

    @GetMapping("/{developerId}/delete")
    public String delete(@PathVariable Integer developerId, Model model){

        LOGGER.debug("DCONTROLLER - delete() DeveloperId = {}. Model = {}", developerId, model);
        developersService.delete(developerId);
        return"redirect:/developers";
    }
}
