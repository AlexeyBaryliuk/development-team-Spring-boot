package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
@SuppressFBWarnings(value = "DLS_DEAD_LOCAL_STORE")
public class ExcelWriteServiceImpl implements ExcelWriteService {

    @Override
    public boolean createProjectExcel(List<Projects> projects
            , ServletContext context
            , HttpServletRequest httpServletRequest
            , HttpServletResponse response) {

      File file = createFile(context);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file + "/" + "projects" + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("projects");
            worksheet.setDefaultColumnWidth(30);

            HSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setItalic(true);
            // Font Height
            font.setFontHeightInPoints((short) 10);
            // Font Color
            font.setColor(IndexedColors.SEA_GREEN.index);

            HSSFCellStyle headerSellStyle = cellStyle(workbook,IndexedColors.SKY_BLUE);

            headerSellStyle.setFont(font);
            HSSFRow headerRow = worksheet.createRow(0);

            HSSFCell projectId = headerRow.createCell(0);
            projectId.setCellValue("projectId");
            projectId.setCellStyle(headerSellStyle);

            HSSFCell description = headerRow.createCell(1);
            description.setCellValue("description");
            description.setCellStyle(headerSellStyle);

            HSSFCell dateAdded = headerRow.createCell(2);
            dateAdded.setCellValue("dateAdded");
            dateAdded.setCellStyle(headerSellStyle);

            int i = 1;
            for (Projects project : projects) {
                HSSFRow bodyRow = worksheet.createRow(i);

                HSSFCellStyle bodySellStyle = cellStyle(workbook,IndexedColors.WHITE);

                HSSFCell projectIdValue = bodyRow.createCell(0);
                projectIdValue.setCellValue(project.getProjectId());
                projectIdValue.setCellStyle(bodySellStyle);

                HSSFCell descriptionValue = bodyRow.createCell(1);
                descriptionValue.setCellValue(project.getDescription());
                descriptionValue.setCellStyle(bodySellStyle);

                HSSFCell dateAddedValue = bodyRow.createCell(2);
                dateAddedValue.setCellValue(project.getDateAdded().toString());
                dateAddedValue.setCellStyle(bodySellStyle);

                i++;
            }
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean createDeveloperExcel(List<Developers> developers
            , ServletContext context
            , HttpServletRequest request
            , HttpServletResponse response) {

        File file = createFile(context);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file + "/" + "developers" + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("developers");
            worksheet.setDefaultColumnWidth(30);

            HSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setItalic(true);
            // Font Height
            font.setFontHeightInPoints((short) 10);
            // Font Color
            font.setColor(IndexedColors.WHITE.index);

            HSSFCellStyle headerSellStyle = cellStyle(workbook,IndexedColors.SKY_BLUE);

            headerSellStyle.setFont(font);

            HSSFRow headerRow = worksheet.createRow(0);

            HSSFCell developerId = headerRow.createCell(0);
            developerId.setCellValue("developerId");
            developerId.setCellStyle(headerSellStyle);

            HSSFCell firstName = headerRow.createCell(1);
            firstName.setCellValue("firstName");
            firstName.setCellStyle(headerSellStyle);

            HSSFCell lastName = headerRow.createCell(2);
            lastName.setCellValue("lastName");
            lastName.setCellStyle(headerSellStyle);

            int i = 1;
            for (Developers developer : developers) {
                HSSFRow bodyRow = worksheet.createRow(i);

                HSSFCellStyle bodySellStyle = cellStyle(workbook,IndexedColors.WHITE);

                HSSFCell developerIdValue = bodyRow.createCell(0);
                developerIdValue.setCellValue(developer.getDeveloperId());
                developerIdValue.setCellStyle(bodySellStyle);

                HSSFCell firstNameValue = bodyRow.createCell(1);
                firstNameValue.setCellValue(developer.getFirstName());
                firstNameValue.setCellStyle(bodySellStyle);

                HSSFCell lastNameValue = bodyRow.createCell(2);
                lastNameValue.setCellValue(developer.getLastName());
                lastNameValue.setCellStyle(bodySellStyle);

                i++;
            }
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }


    private File createFile(ServletContext context){
        String filePath = context.getRealPath("/resources");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + filePath);
        File file = new File(filePath);

        boolean exists = file.exists() || new File(filePath).mkdir();

        if (!exists) {
            try {
                throw new IOException("Unable to create path");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private HSSFCellStyle cellStyle(HSSFWorkbook workbook, IndexedColors indexedColors){

        HSSFCellStyle sellStyle = workbook.createCellStyle();
        sellStyle.setFillForegroundColor(indexedColors.index);
        sellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        sellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        sellStyle.setAlignment(HorizontalAlignment.CENTER);
        sellStyle.setBorderBottom(BorderStyle.THIN);
        sellStyle.setBorderRight(BorderStyle.THIN);
        sellStyle.setBorderLeft(BorderStyle.THIN);
        sellStyle.setBorderTop(BorderStyle.THIN);

        return sellStyle;
    }
}
