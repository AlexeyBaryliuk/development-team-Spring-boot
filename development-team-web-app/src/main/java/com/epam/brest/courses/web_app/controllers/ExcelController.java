package com.epam.brest.courses.web_app.controllers;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import com.epam.brest.courses.service.excel.ExcelFileImportService;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * ExcelController.
 */
@Controller
@RequestMapping
public class ExcelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ExcelFileExportService exportService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private DevelopersService developersService;

    @Autowired
    private final ExcelFileImportService excelFileImportServiceRest;

    public ExcelController( ExcelFileImportService excelFileImportServiceRest) {
        this.excelFileImportServiceRest = excelFileImportServiceRest;
    }



    @PostMapping("/projects/import")
    public String projectsImportFile(@ModelAttribute ("projectExcel")  Projects project
                            , @RequestParam("file") MultipartFile file){
        LOGGER.debug("Project {} = ", project);
        LOGGER.debug("importFile({})", file.getSize());
        project.setMultipartFile(file);
        excelFileImportServiceRest.saveProjectsDataFromUploadFile(project.getMultipartFile());
        return "redirect:/projects";
    }

    @PostMapping("/developers/import")
    public String developersImportFile(@ModelAttribute("developerExcel") Developers developer
            , @RequestParam("file") MultipartFile file){

        LOGGER.debug("Developer {} = ", developer);
        LOGGER.debug("importFile({})", file.getSize());
        developer.setMultipartFile(file);
        excelFileImportServiceRest.saveDevelopersDataFromUploadFile(developer.getMultipartFile());
        return "redirect:/developers";
    }

    @GetMapping("/projects/download")
    public void projectsDownload(HttpServletResponse response) throws IOException {

        LOGGER.debug("projectsDownload()");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = projects.xlsx");
        ByteArrayInputStream stream = exportService.exportProjectsToExcel(projectsService.findAll());
        IOUtils.copy(stream, response.getOutputStream());

    }
    @GetMapping("/developers/download")
    public void developersDownload(HttpServletResponse response) throws IOException {


        LOGGER.debug("developersDownload()");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = developers.xlsx");
        ByteArrayInputStream stream = exportService.exportDevelopersToExcel(developersService.findAll());
        IOUtils.copy(stream, response.getOutputStream());

    }


}
