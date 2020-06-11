package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

@Service
@Transactional
public class ExcelFileImportServiceImpl implements ExcelFileImportService {

    @Autowired
    private DevelopersService developersService;

    @Autowired
    private ProjectsService projectsService;

    @Override
    public boolean saveDataFromUploadFile(MultipartFile file) {

        boolean isFlag = false;

        String exstension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (exstension.equalsIgnoreCase("xls") || exstension.equalsIgnoreCase("xlsx")){
            isFlag = readProjectsDataFromExcel(file);
        }
        return isFlag;
    }
    @Override
    public boolean readProjectsDataFromExcel(MultipartFile file) {

        Workbook workbook = getWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        rows.next();
        while (rows.hasNext()){
            Row row = rows.next();
            Projects project = new Projects();
            if (row.getCell(0).getCellType() == CellType.NUMERIC){
                project.setProjectId((int) row.getCell(0).getNumericCellValue());
            }
            if (row.getCell(1).getCellType() == CellType.STRING){
                project.setDescription(row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2).getCellType() == CellType.STRING){
                String dateAdded = row.getCell(2).getStringCellValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formatter = formatter.withLocale( Locale.ENGLISH );
                LocalDate date = LocalDate.parse(dateAdded, formatter);
                project.setDateAdded(date);
            }
            project.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
            projectsService.create(project);
        }

        return true;
    }

    @Override
    public boolean readDevelopersDataFromExcel(MultipartFile file) {
        Workbook workbook = getWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        rows.next();
        while (rows.hasNext()){
            Row row = rows.next();
            Developers developer = new Developers();
            if (row.getCell(0).getCellType() == CellType.NUMERIC){
                developer.setDeveloperId((int) row.getCell(0).getNumericCellValue());
            }
            if (row.getCell(1).getCellType() == CellType.STRING){
                developer.setFirstName(row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2).getCellType() == CellType.STRING){
                developer.setLastName(row.getCell(2).getStringCellValue());
            }
            developer.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
            developersService.create(developer);
        }

        return true;
    }

    private Workbook getWorkbook(MultipartFile file) {

        Workbook workbook = null;
        String exstension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            if (exstension.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            }
            else if(exstension.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        }catch (IOException e) {
                    e.printStackTrace();
                }

        return workbook;
    }
}
