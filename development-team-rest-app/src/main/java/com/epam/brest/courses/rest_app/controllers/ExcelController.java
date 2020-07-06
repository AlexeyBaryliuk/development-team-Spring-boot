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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@SuppressFBWarnings(value = {"DLS_DEAD_LOCAL_STORE","DMI_HARDCODED_ABSOLUTE_FILENAME", "UC_USELESS_OBJECT"})
public class ExcelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

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

    @PostMapping(value = "/projectsImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean importExcelProjects(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        LOGGER.debug("MultipartFile for projects = {}", multipartFile.getOriginalFilename());

        return excelFileImportService.saveProjectsDataFromUploadFile(multipartFile);
    }

    @PostMapping(value = "/developersImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean importExcelDevelopers(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        LOGGER.debug("MultipartFile for developers = {}", multipartFile.getSize());

        return excelFileImportService.saveDevelopersDataFromUploadFile(multipartFile);
    }
}