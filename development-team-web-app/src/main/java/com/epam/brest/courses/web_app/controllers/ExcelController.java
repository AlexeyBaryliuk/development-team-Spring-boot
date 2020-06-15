package com.epam.brest.courses.web_app.controllers;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.ExcelFileImportService;
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

    @PostMapping("/import")
    public String importFile(@ModelAttribute   Projects project
                            , @RequestParam("file") MultipartFile file){

        LOGGER.debug("importFile({})", file.getSize());
        project.setMultipartFile(file);
        excelFileImportServiceRest.saveDataFromUploadFile(project.getMultipartFile());
        return "redirect:/projects";
    }



}
