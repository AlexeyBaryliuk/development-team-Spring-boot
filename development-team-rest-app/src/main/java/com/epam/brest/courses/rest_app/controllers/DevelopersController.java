package com.epam.brest.courses.rest_app.controllers;


import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.rest_app.exception.ErrorResponse;
import com.epam.brest.courses.service.DevelopersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * DevelopersController.
 */
@RestController
@RequestMapping("/developers")
public class DevelopersController {


    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersController.class);

    public static final String DEVELOPER_NOT_FOUND = "developer.not_found";

    private final DevelopersServiceImpl developersService;

    public DevelopersController(DevelopersServiceImpl developersService) {
        this.developersService = developersService;
    }

    /**
     * Goto developers list page
     *
     * @return List of developers.
     */
    @GetMapping
    public Collection<Developers> findAll(){

        LOGGER.debug("findAll()");

        return developersService.findAll();
    }

    /**
     * Find developer by id.
     *
     * @param developerId
     * @return Errorrespons.
     */
    @GetMapping("/{developerId}")
    public ResponseEntity<Developers> findByDeveloperId(@PathVariable Integer developerId){

        LOGGER.debug("findByDeveloperId({})", developerId);
        Optional<Developers> optionalDeveloper = developersService.findByDeveloperId(developerId);

        return optionalDeveloper.isPresent()
                ?new ResponseEntity<>(optionalDeveloper.get(),HttpStatus.OK)
                :new ResponseEntity(
                        new ErrorResponse(DEVELOPER_NOT_FOUND, Arrays.asList("Devloper with developerId = " + developerId + " not found"))
                                         ,HttpStatus.NOT_FOUND);
    }

    /**
     *Create new developer.
     *
     * @param developer
     * @return Developer's id.
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Integer create(@RequestBody Developers developer){

        LOGGER.debug("create() Developer {}", developer);

        return developersService.create(developer);
    }


    /**
     * Update developer.
     *
     * @param developer
     * @return ResponsEntity.
     */
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> update(@RequestBody Developers developer){

        LOGGER.debug("update() - Developer {}", developer);
    Integer result = developersService.update(developer);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /**
     * delete developer by id.
     *
     * @param developerId
     * @return ResponsEntity.
     */
    @DeleteMapping(value = "/{developerId}", produces = "application/json")
    public ResponseEntity<Integer> delete(@PathVariable Integer developerId){

        LOGGER.debug("delete({})", developerId);

        Integer result = developersService.deleteByDeveloperId(developerId);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /**
     * Delete all developers.
     *
     * @return ResponsEntity.
     */
    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<Integer> deleteAllDevelopers(){

        LOGGER.debug("deleteAllDevelopers()");

        Integer result = developersService.deleteAllDevelopers();

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
