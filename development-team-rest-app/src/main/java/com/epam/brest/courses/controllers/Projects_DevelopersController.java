package com.epam.brest.courses.controllers;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import com.epam.brest.courses.service.Projects_DevelopersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;



@RestController
@RequestMapping("/projects_developers")
public class Projects_DevelopersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects_DevelopersController.class);

    private final Projects_DevelopersService projects_developersService;

    public Projects_DevelopersController(Projects_DevelopersService projects_developersService) {
        this.projects_developersService = projects_developersService;
    }

    /**
     * Goto developers from projects_developers.
     *
     * @param projectId
     * @return Developer's list.
     */
    @GetMapping("/{projectId}")
    public Collection<Developers> selectDevelopersFromProjects_Developers(@PathVariable Integer projectId){

        LOGGER.debug("selectDevelopersFromProjects_Developers({})", projectId);

        return projects_developersService.selectDevelopersFromProjects_Developers(projectId);
}

    /**
     * Add developer to projects_developers by projectId.
     *
     * @param projectId
     * @param developerId
     * @return ResponseEntity.
     */
    @PostMapping("/{projectId}/{developerId}")
    public ResponseEntity<Integer> addDeveloperToProjects_Developers(@PathVariable Integer projectId
                                                                    ,@PathVariable Integer developerId){

        LOGGER.debug("RESTaddDeveloperToProjects_Developers({},{})", projectId,developerId);

        Integer result = projects_developersService.addDeveloperToProjects_Developers(projectId,developerId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Delete developer from projects_developers.
     *
     * @param projectId
     * @param developerId
     * @return ResponseEntity.
     */
    @DeleteMapping("/{projectId}/{developerId}")
    public ResponseEntity<Integer> deleteDeveloperFromProject_Developers(@PathVariable Integer projectId
                                                                        ,@PathVariable Integer developerId) {

        LOGGER.debug("deleteDeveloperFromProject_Developers({},{})", projectId,developerId);

        Integer result = projects_developersService.deleteDeveloperFromProject_Developers(projectId,developerId);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    /**
     * Find by both id from projects_developers.
     *
     * @param projectId
     * @param developerId
     * @return  Optional<Projects_Developers> projects_developersList.
     */
    @GetMapping("/{projectId}/{developerId}")
    public ResponseEntity<Projects_Developers> findByIdFromProjects_Developers(@PathVariable Integer projectId
                                                                       , @PathVariable Integer developerId) {

        LOGGER.debug("findByIdFromProjects_Develoers({},{})",projectId,developerId);
        Optional<Projects_Developers> projects_developer = projects_developersService
                                                                .findByIdFromProjects_Developers(projectId,developerId);
        LOGGER.debug("______PROJECTS_DEVELOPERS______({})",projects_developer);
        return projects_developer.isPresent()
                ?new ResponseEntity<>(projects_developer.get(),HttpStatus.OK)
                :new ResponseEntity(Optional.empty() ,HttpStatus.OK);

    }

}
