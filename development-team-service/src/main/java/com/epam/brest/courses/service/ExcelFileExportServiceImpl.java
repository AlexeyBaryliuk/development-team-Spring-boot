package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelFileExportServiceImpl implements ExcelFileExportService {

    @Override
    public  ByteArrayInputStream exportProjectsToExcel(List<Projects> projectsList) {


        try {
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("projects");

            Row row = sheet.createRow(0);

            CellStyle headerSellStyle = cellStyle(workbook, IndexedColors.SKY_BLUE);

            Cell cell = row.createCell(0);
            cell.setCellValue("projectId");
            cell.setCellStyle(headerSellStyle);

            cell = row.createCell(1);
            cell.setCellValue("description");
            cell.setCellStyle(headerSellStyle);

            cell = row.createCell(2);
            cell.setCellValue("dateAdded");
            cell.setCellStyle(headerSellStyle);

            for (int i = 0; i < projectsList.size(); i++) {

                Row dataRow = sheet.createRow(i + 1);
                CellStyle bodySellStyle = cellStyle(workbook, IndexedColors.WHITE);

                Cell bodyCell = dataRow.createCell(0);
                bodyCell.setCellValue(projectsList.get(i).getProjectId());
                bodyCell.setCellStyle(bodySellStyle);

                bodyCell = dataRow.createCell(1);
                bodyCell.setCellValue(projectsList.get(i).getDescription());
                bodyCell.setCellStyle(bodySellStyle);

                bodyCell = dataRow.createCell(2);
                bodyCell.setCellValue(projectsList.get(i).getDateAdded().toString());
                bodyCell.setCellStyle(bodySellStyle);
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public ByteArrayInputStream exportDevelopersToExcel(List<Developers> developersList) {

        try {
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("developers");

            Row row = sheet.createRow(0);

            CellStyle headerSellStyle = cellStyle(workbook, IndexedColors.SKY_BLUE);

            Cell cell = row.createCell(0);
            cell.setCellValue("developerId");
            cell.setCellStyle(headerSellStyle);

            cell = row.createCell(1);
            cell.setCellValue("firstName");
            cell.setCellStyle(headerSellStyle);

            cell = row.createCell(2);
            cell.setCellValue("lastName");
            cell.setCellStyle(headerSellStyle);

            for (int i = 0; i < developersList.size(); i++) {

                Row dataRow = sheet.createRow(i + 1);
                CellStyle bodySellStyle = cellStyle(workbook, IndexedColors.WHITE);

                Cell bodyCell = dataRow.createCell(0);
                bodyCell.setCellValue(developersList.get(i).getDeveloperId());
                bodyCell.setCellStyle(bodySellStyle);

                bodyCell = dataRow.createCell(1);
                bodyCell.setCellValue(developersList.get(i).getFirstName());
                bodyCell.setCellStyle(bodySellStyle);

                bodyCell = dataRow.createCell(2);
                bodyCell.setCellValue(developersList.get(i).getLastName());
                bodyCell.setCellStyle(bodySellStyle);
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }


    private CellStyle cellStyle(Workbook workbook, IndexedColors indexedColors){

        CellStyle sellStyle = workbook.createCellStyle();
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
