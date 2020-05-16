package com.epam.brest.courses.web_app.controllers;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.Projects_DevelopersService;
import com.epam.brest.courses.web_app.validators.ProjectsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Projects controller.
 */
@Controller
@RequestMapping("/projects")
public class ProjectsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsController.class);

    @Autowired
    private final ProjectsDtoService projectsDtoService;

    @Autowired
    private final ProjectsService projectsService;

    @Autowired
    private final DevelopersService developersService;

    @Autowired
    private final Projects_DevelopersService projects_developersService;

    public ProjectsController(ProjectsDtoService projectsDtoService
                            , ProjectsService projectsService
                            , DevelopersService developersService
                            , Projects_DevelopersService projects_developersService) {
        this.projectsDtoService = projectsDtoService;
        this.projectsService = projectsService;
        this.developersService = developersService;
        this.projects_developersService = projects_developersService;
    }

        @Autowired
        ProjectsValidator projectsValidator;

        Developers developer = new Developers();

    /**
     * Goto projects list page.
     *
     * @return view name
     */
    @GetMapping
    public final String projects(
                                 @RequestParam (value = "dateStart", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                 @RequestParam (value = "dateEnd", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd,
                                 Model model){

     if (dateStart!= null && dateEnd != null){

         LOGGER.debug("Find projects between dates. Date start = {}, Date End = {}", dateStart, dateEnd);
         List<ProjectsDto> projectsDtoListBetween = projectsDtoService.findBetweenDates(dateStart, dateEnd);
                 LOGGER.debug("______________________ {}", projectsDtoListBetween);
         model.addAttribute("projects", projectsDtoListBetween);
    }
    else{

         LOGGER.debug("Find all projects");

         List<ProjectsDto> projectsDtoList = projectsDtoService.countOfDevelopers();
         model.addAttribute("projects", projectsDtoList );
    }

  return "projects";
}

    /**
     * Goto edit project's page.
     *
     * @return view name
     */
    @GetMapping(value = "/{projectId}")
    public final String gotoEditProjectsPage(@PathVariable Integer projectId
                                            ,@RequestParam(required = false) Integer developerId
                                            , Model model) {

        if (developerId != null && projects_developersService.findByIdFromProjects_Developers(projectId, developerId).get().getProjectId() == null) {

            projects_developersService.addDeveloperToProjects_Developers(projectId, developerId);
            LOGGER.debug("Developer with developerId = {} added to projects_developers", developerId);
        }

        LOGGER.debug("CONTROLLER - gotoEditProjectsPage({},{})", projectId, model);
        Optional<Projects> optionalProjects = projectsService.findById(projectId);
        if (optionalProjects.isPresent()) {
            model.addAttribute("project", optionalProjects.get());
            model.addAttribute("developers", projects_developersService.selectDevelopersFromProjects_Developers(projectId));
            model.addAttribute("developersList", developersService.findAll());
            model.addAttribute("developerEntity", developer);
            return "project";
        } else {
            // TODO department not found - pass error message as parameter or handle not found error
            return "redirect:/projects";
        }
    }

    /**
     * Update department.
     *
     * @param project project with filled data.
     * @param result     binding result
     * @return view name
     */
    @PostMapping(value = "/{id}")
    public String updateProject(@ModelAttribute("project") @Valid Projects project,
                                BindingResult result, Model model){

        model.addAttribute("developerEntity", developer);
        LOGGER.debug("updateProject({}, {})", project, result);
        projectsValidator.validate(project, result);
        if (result.hasErrors()) {
            return "project";
        } else {
            this.projectsService.update(project);
            return "redirect:/projects";
        }
    }

    /**
     * Goto add project page.
     *
     * @return view name
     */
    @GetMapping(value = "/add")
    public final String gotoAddProjectPage(Model model) {

        LOGGER.debug("gotoAddProjectPage({})", model);
        model.addAttribute("project", new Projects());
        return "projectAdd";
    }

    /**
     * Persist new project into persistence storage.
     *
     * @param project new project with filled data.
     * @param result     binding result.
     * @return view name
     */
    @PostMapping(value = "/add")
    public String addProject(@ModelAttribute("project")
                             @Valid Projects project,
                             BindingResult result,Model model) {

        LOGGER.debug("addProject{}, {})", project, result);
        projectsValidator.validate(project, result);
        if (result.hasErrors()) {

            return "projectAdd";
        } else {
            try {
                this.projectsService.create(project);
            }
                catch (IllegalArgumentException ie){
                    result.rejectValue("description", "projectDescription.exist");
                    return "projectAdd";
                }

            return "redirect:/projects";
        }
    }


    /**
     * Delete project.
     *
     * @return view name
     */
    @GetMapping(value = "/{id}/delete")
    public final String deleteProjectById(@PathVariable Integer id, Model model) {

        LOGGER.debug("delete({},{})", id, model);
        projectsService.delete(id);
        return "redirect:/projects";
    }

    /**
     * Delete project.
     *
     * @return view name
     */
    @GetMapping(value = "/{projectId}/{developerId}/delete")
    public final String deleteDeveloperFromProjects_developers( @PathVariable Integer projectId
                                                               ,@PathVariable Integer developerId
                                                               ,Model model) {

        LOGGER.debug("delete({},{}{})", projectId, developerId, model);
        projects_developersService.deleteDeveloperFromProject_Developers(projectId,developerId);
        return "redirect:/projects/" + projectId;
    }

}
