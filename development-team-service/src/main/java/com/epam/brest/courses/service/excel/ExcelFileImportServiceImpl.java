package com.epam.brest.courses.service.excel;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

@Service
@SuppressFBWarnings(value = {"DLS_DEAD_LOCAL_STORE"})
public class ExcelFileImportServiceImpl implements ExcelFileImportService, ExcelReadDataFromFile {

    @Autowired
    private DevelopersService developersService;

    @Autowired
    private ProjectsService projectsService;

    @Override
    public boolean saveProjectsDataFromUploadFile(MultipartFile file) {

        boolean isFlag = false;
        System.out.println("_________________________________________{}" + file.getOriginalFilename());
        String exstension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (exstension.equalsIgnoreCase("xls") || exstension.equalsIgnoreCase("xlsx")){
            isFlag = readProjectsDataFromExcel(file);
        }
        return isFlag;
    }

    @Override
    public boolean readProjectsDataFromExcel(MultipartFile file) {

        Workbook workbook = getWorkbook(file);
        Integer num = 0;
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        rows.next();
        while (rows.hasNext()){

            Row row = rows.next();
            Projects project = new Projects();
            try {
            if (row.getCell(0).getCellType() == CellType.NUMERIC){
                project.setProjectId((int) row.getCell(0).getNumericCellValue());
            }


                if (row.getCell(1).getCellType() == CellType.STRING) {
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
            catch (IllegalArgumentException ex){
                project.setDescription("Project with same description already exist. Count of repeat = " + num);
                num++;
                projectsService.create(project);
            }
        }

        return true;
    }

    @Override
    public boolean saveDevelopersDataFromUploadFile(MultipartFile file) {

        boolean isFlag = false;
        System.out.println("_________________________________________{}" + file.getOriginalFilename());
        String exstension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (exstension.equalsIgnoreCase("xls") || exstension.equalsIgnoreCase("xlsx")){
            isFlag = readDevelopersDataFromExcel(file);
        }
        return isFlag;
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

    public Workbook getWorkbook(MultipartFile file) {

        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            if (extension.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            }
            else if(extension.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        }catch (IOException e) {
                    e.printStackTrace();
                }

        return workbook;
    }
}
