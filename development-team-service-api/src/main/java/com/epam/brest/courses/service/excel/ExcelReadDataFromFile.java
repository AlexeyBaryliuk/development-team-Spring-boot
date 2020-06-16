package com.epam.brest.courses.service.excel;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelReadDataFromFile {

    boolean readProjectsDataFromExcel(MultipartFile file);

    boolean readDevelopersDataFromExcel(MultipartFile file);
}
