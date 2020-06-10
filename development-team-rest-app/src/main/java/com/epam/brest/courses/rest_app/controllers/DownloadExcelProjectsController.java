package com.epam.brest.courses.rest_app.controllers;

import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ExcelFileExportService;
import com.epam.brest.courses.service.ProjectsService;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping
public class DownloadExcelProjectsController {

    @Autowired
    private ExcelFileExportService excelFileExportService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private DevelopersService developersService;

    @GetMapping("/download/projects.xlsx")
    public void downloadExcelProjects(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = projects.xlsx");
        ByteArrayInputStream stream = excelFileExportService.exportProjectsToExcel(projectsService.findAll());
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/download/developers.xlsx")
    public void downloadExcelDevelopers(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = developers.xlsx");
        ByteArrayInputStream stream = excelFileExportService.exportDevelopersToExcel(developersService.findAll());
        IOUtils.copy(stream, response.getOutputStream());
    }
}
