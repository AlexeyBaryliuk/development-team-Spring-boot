package com.epam.brest.courses.web_app.controllers;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.excel.ExcelFileImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * ExcelController.
 */
@Controller
@RequestMapping
public class ExcelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private final ExcelFileImportService excelFileImportServiceRest;

    public ExcelController( ExcelFileImportService excelFileImportServiceRest) {
        this.excelFileImportServiceRest = excelFileImportServiceRest;
    }

    @PostMapping("/projects/import")
    public String projectsImportFile(@ModelAttribute   Projects project
                            , @RequestParam("file") MultipartFile file){

        LOGGER.debug("importFile({})", file.getSize());
        project.setMultipartFile(file);
        excelFileImportServiceRest.saveProjectsDataFromUploadFile(project.getMultipartFile());
        return "redirect:/projects";
    }

    @PostMapping("/developers/import")
    public String developersImportFile(@ModelAttribute   Projects project
            , @RequestParam("file") MultipartFile file){

        LOGGER.debug("importFile({})", file.getSize());
        project.setMultipartFile(file);
        excelFileImportServiceRest.saveDevelopersDataFromUploadFile(project.getMultipartFile());
        return "redirect:/developers";
    }


}
