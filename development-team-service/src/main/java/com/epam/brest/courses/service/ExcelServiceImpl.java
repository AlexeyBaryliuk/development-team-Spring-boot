package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Projects;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
public class ExcelServiceImpl implements ExcelService {

    @Override
    public boolean createProjectExcel(List<Projects> projects
            , ServletContext context
            , HttpServletRequest httpServletRequest
            , HttpServletResponse response) {

        String filePath = context.getRealPath("/resources");
        File file = new File(filePath);

        boolean exists = file.exists() || new File(filePath).mkdir();

        if (!exists) {
            try {
                throw new IOException("Unable to create path");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

            HSSFCellStyle headerSellStyle = workbook.createCellStyle();
            headerSellStyle.setFillBackgroundColor(IndexedColors.LIME.index);
            headerSellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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

                HSSFCellStyle bodySellStyle = workbook.createCellStyle();
                headerSellStyle.setFillForegroundColor(IndexedColors.RED.index);

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
}
