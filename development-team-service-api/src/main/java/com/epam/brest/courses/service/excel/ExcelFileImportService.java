package com.epam.brest.courses.service.excel;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelFileImportService {

    boolean saveProjectsDataFromUploadFile(MultipartFile multipartFile);

    boolean saveDevelopersDataFromUploadFile(MultipartFile multipartFile);

}
