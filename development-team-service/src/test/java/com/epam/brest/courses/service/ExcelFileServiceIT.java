package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import com.epam.brest.courses.service.testConfig.TestConfig;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={Developers.class, Projects.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
public class ExcelFileServiceIT {

    @Autowired
    private ExcelFileExportService excelFileExportService;

    @Autowired
    private ExcelFileImportServiceImpl excelFileImportService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private DevelopersService developersService;

    @Test
    public void shouldexportAndImportProjectsInExcel() throws IOException {

        List<Projects> projectsList = projectsService.findAll();

        ByteArrayInputStream stream = excelFileExportService.exportProjectsToExcel(projectsList);

        String expectedresult = IOUtils.toString(stream, StandardCharsets.UTF_8);

        assertFalse(expectedresult.isEmpty());

    }


}
