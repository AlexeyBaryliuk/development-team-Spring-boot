package com.epam.brest.courses.rest_app.controllers;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ExcelFileExportService;
import com.epam.brest.courses.service.ExcelFileImportService;
import com.epam.brest.courses.service.ProjectsService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping
@SuppressFBWarnings(value = {"DLS_DEAD_LOCAL_STORE","DMI_HARDCODED_ABSOLUTE_FILENAME"})
public class DownloadExcelProjectsController {

    @Autowired
    private ExcelFileExportService excelFileExportService;

    @Autowired
    private ExcelFileImportService excelFileImportService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private DevelopersService developersService;

    @GetMapping("/projectsDownload")
    public void downloadExcelProjects(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = projects.xlsx");
        ByteArrayInputStream stream = excelFileExportService.exportProjectsToExcel(projectsService.findAll());
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/developersDownload")
    public void downloadExcelDevelopers(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = developers.xlsx");
        ByteArrayInputStream stream = excelFileExportService.exportDevelopersToExcel(developersService.findAll());
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping(value = "/import/projects.xlsx")
    public String importExcelProjects(Projects project) throws IOException {

        if(project == null){
           project = new Projects();

        File file = new File("/home/alexey/Загрузки/projects.xlsx");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFileNew = new MockMultipartFile("file"
                , file.getName()
                , "text/plain"
                , IOUtils.toByteArray(input));

        project.setMultipartFile(multipartFileNew);
        }

         excelFileImportService.saveDataFromUploadFile(project.getMultipartFile());
         return "Import was successful";
    }

    @GetMapping("/developersImport")
    public String importExcelDevelopers(Developers developer) throws IOException {

        if(developer == null){
           developer = new Developers();
            File file = new File("/home/alexey/Загрузки/projects.xlsx");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file"
                , file.getName()
                ,"text/plain"
                , IOUtils.toByteArray(input));

        developer.setMultipartFile(multipartFile);

        }
        excelFileImportService.saveDataFromUploadFile(developer.getMultipartFile());
        return "Import was successful";
    }

}
