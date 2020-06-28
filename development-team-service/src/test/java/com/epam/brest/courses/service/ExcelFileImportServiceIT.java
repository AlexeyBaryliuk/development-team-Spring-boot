package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.testConfig.TestConfig;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={Developers.class, Projects.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
class ExcelFileImportServiceIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileExportServiceIT.class);

    @Autowired
    private ExcelFileImportServiceImpl excelFileImportService;

    @Autowired
    private ExcelFileExportServiceImpl exportService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private DevelopersService developersService;

    @Test
    void shouldSaveProjectsDataFromUploadFile() throws IOException {

        File file = new File("/home/alexey/Загрузки/projects.xlsx");
        MultipartFile multipartFile = createMultipartFile(file);

        boolean result = excelFileImportService.saveProjectsDataFromUploadFile(multipartFile);

        assertTrue(result);
    }

    @Test
    void shouldReadProjectsDataFromExcel() throws IOException {

        File file = new File("/home/alexey/Загрузки/projects.xlsx");
        MultipartFile multipartFile = createMultipartFile(file);

        boolean result = excelFileImportService.readProjectsDataFromExcel(multipartFile);

        assertTrue(result);

    }

    @Test
    void shouldSaveDevelopersDataFromUploadFile() throws IOException {

        File file = new File("/home/alexey/Загрузки/developers.xlsx");
        MultipartFile multipartFile = createMultipartFile(file);

        boolean result = excelFileImportService.saveDevelopersDataFromUploadFile(multipartFile);

        assertTrue(result);
    }

    @Test
    void shouldReadDevelopersDataFromExcel() throws IOException {

        File file = new File("/home/alexey/Загрузки/developers.xlsx");
        MultipartFile multipartFile = createMultipartFile(file);

        boolean result = excelFileImportService.readDevelopersDataFromExcel(multipartFile);

        assertTrue(result);

    }

    @Test
    public void shouldReturnXSSFWorkbook() throws IOException {

        File file = new File("/home/alexey/Загрузки/developers.xlsx");
        MultipartFile multipartFile = createMultipartFile(file);

        Workbook workbook = excelFileImportService.getWorkbook(multipartFile);
        LOGGER.debug("Workbook name = {}",workbook.getClass());
        assertEquals(workbook.getClass(), XSSFWorkbook.class);

    }


    public MultipartFile createMultipartFile(File file) throws IOException {

        MultipartFile multipartFile;
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file"
                , file.getName()
                , "text/plain"
                , IOUtils.toByteArray(input));

        return multipartFile;
    }
}