package com.epam.brest.courses.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelFileImportService {

    boolean saveDataFromUploadFile(MultipartFile multipartFile);
}
