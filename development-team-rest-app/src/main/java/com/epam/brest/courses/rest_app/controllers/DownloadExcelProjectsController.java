package com.epam.brest.courses.rest_app.controllers;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import com.epam.brest.courses.service.excel.ExcelFileImportService;
import com.epam.brest.courses.service.ProjectsService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@SuppressFBWarnings(value = {"DLS_DEAD_LOCAL_STORE","DMI_HARDCODED_ABSOLUTE_FILENAME", "UC_USELESS_OBJECT"})
public class DownloadExcelProjectsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadExcelProjectsController.class);

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

    @GetMapping(value = "/projectsImport")
    public boolean importExcelProjects(@RequestBody MultipartFile multipartFile) throws IOException {

        LOGGER.debug("MultipartFile for projects = {}", multipartFile);
        if (multipartFile == null) {

            Projects project = new Projects();

        File file = new File("/home/alexey/Загрузки/projects.xlsx");

        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFileNew = new MockMultipartFile("file"
                , file.getName()
                , "text/plain"
                , IOUtils.toByteArray(input));

        project.setMultipartFile(multipartFileNew);
        multipartFile =  project.getMultipartFile();

        }
         boolean isFlag = excelFileImportService.saveProjectsDataFromUploadFile(multipartFile);
         return isFlag;
    }

    @GetMapping("/developersImport")
    public boolean importExcelDevelopers(@RequestBody MultipartFile multipartFile) throws IOException {

        LOGGER.debug("MultipartFile for developers = {}", multipartFile);
        if(multipartFile == null){
           Developers developer = new Developers();

            File file = new File("/home/alexey/Загрузки/developers.xlsx");
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file"
                , file.getName()
                ,"text/plain"
                , IOUtils.toByteArray(input));

        developer.setMultipartFile(multipartFile);
        }

        boolean isFlag = excelFileImportService.saveDevelopersDataFromUploadFile(multipartFile);
        return isFlag;
    }

}
