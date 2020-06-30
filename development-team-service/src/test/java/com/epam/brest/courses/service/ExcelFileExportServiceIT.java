package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import com.epam.brest.courses.service.testConfig.TestConfig;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes={Developers.class, Projects.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
public class ExcelFileExportServiceIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileExportServiceIT.class);

    @Autowired
    private ExcelFileExportService excelFileExportService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private DevelopersService developersService;

    @Test
    public void shouldExportProjectsFromExcel() throws IOException {

        int result = 0;
        Projects project = new Projects();
        project.setDescription("Test");
        Integer id = projectsService.create(project);

        List<Projects> projectsList = new ArrayList<>();
        projectsList.add(projectsService.findByProjectId(id).get());

        ByteArrayInputStream streamForWorkBook = excelFileExportService.exportProjectsToExcel(projectsList);

        int numberOfProjects = 0;
        XSSFWorkbook workbook = new XSSFWorkbook(streamForWorkBook);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            numberOfProjects++;
            for (Cell cell : row) {
                CellType cellType = cell.getCellType();
                if (row.getRowNum() == 1 && cell.getColumnIndex() == 0 && cellType == CellType.NUMERIC) {
                    result = (int) row.getCell(0).getNumericCellValue();
                }
            }
        }
        assertEquals(numberOfProjects-1, projectsList.size());
        assertEquals((long)id,result );

    }

    @Test
    public void shouldExportDevelopersFromExcel() throws IOException {

        int result = 0;
        Developers developer = new Developers();
        developer.setFirstName("Jone");
        developer.setLastName("Testing");
        Integer developerId = developersService.create(developer);
        List<Developers> developersList = new ArrayList<>();

        developersList.add(developersService.findByDeveloperId(developerId).get());


        ByteArrayInputStream streamForWorkBook = excelFileExportService.exportDevelopersToExcel(developersList);

        int numberOfDevelopers = 0;
        XSSFWorkbook workbook = new XSSFWorkbook(streamForWorkBook);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            numberOfDevelopers++;

            for (Cell cell : row) {
                CellType cellType = cell.getCellType();
                if (row.getRowNum() == 1 && cell.getColumnIndex() == 0 && cellType == CellType.NUMERIC) {
                    result = (int) row.getCell(0).getNumericCellValue();
                }
            }
        }
        LOGGER.debug("____________________________________________________result = {}", result);
        assertEquals(numberOfDevelopers-1, developersList.size());
        assertEquals((long)developerId, result);

    }
}
