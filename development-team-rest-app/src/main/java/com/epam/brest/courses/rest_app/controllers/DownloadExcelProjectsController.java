package com.epam.brest.courses.rest_app.controllers;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import com.epam.brest.courses.service.excel.ExcelFileImportService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@SuppressFBWarnings(value = {"DLS_DEAD_LOCAL_STORE","DMI_HARDCODED_ABSOLUTE_FILENAME", "UC_USELESS_OBJECT"})
public class DownloadExcelProjectsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadExcelProjectsController.class);

    @Autowired
    private ExcelFileExportService excelFileExportService;

    @Autowired
    private ExcelFileImportService excelFileImportService;

    @PostMapping(value = "/projectsDownload" , consumes = "application/json")
    public ResponseEntity<byte[]> downloadExcelProjects(@RequestBody List<Projects> projectsList) throws IOException {

        ByteArrayInputStream stream = excelFileExportService.exportProjectsToExcel(projectsList);

        return  ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(IOUtils.toByteArray(stream));
    }

    @PostMapping(value = "/developersDownload", consumes = "application/json")
    public  ResponseEntity<byte[]>  downloadExcelDevelopers(@RequestBody List<Developers> developersList) throws IOException {

        ByteArrayInputStream stream = excelFileExportService.exportDevelopersToExcel(developersList);

        return  ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(IOUtils.toByteArray(stream));
    }

    @GetMapping(value = "/projectsImport")
    public boolean importExcelProjects(@RequestBody MultipartFile multipartFile) throws IOException {

        LOGGER.debug("MultipartFile for projects = {}", multipartFile);

        if (multipartFile == null) {

        File file = new File("/home/alexey/Загрузки/projects.xlsx");

        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file"
                , file.getName()
                , "text/plain"
                , IOUtils.toByteArray(input));

        }
        return excelFileImportService.saveProjectsDataFromUploadFile(multipartFile);
    }

    @GetMapping("/developersImport")
    public boolean importExcelDevelopers(@RequestBody MultipartFile multipartFile) throws IOException {

        LOGGER.debug("MultipartFile for developers = {}", multipartFile);
        if(multipartFile == null){

            File file = new File("/home/alexey/Загрузки/developers.xlsx");
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file"
                , file.getName()
                ,"text/plain"
                , IOUtils.toByteArray(input));

        }

        return excelFileImportService.saveDevelopersDataFromUploadFile(multipartFile);
    }

}
