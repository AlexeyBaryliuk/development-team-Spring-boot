package com.epam.brest.courses.service_rest;


import com.epam.brest.courses.service.excel.ExcelFileImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

public class ExcelFileImportServiceRest implements ExcelFileImportService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileImportServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ExcelFileImportServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean saveProjectsDataFromUploadFile(MultipartFile multipartFile) {

        LOGGER.debug("saveDataFromUploadFile({})",multipartFile.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.MULTIPART_FORM_DATA));
        HttpEntity <MultipartFile> entity = new HttpEntity<>(multipartFile, headers);
        ResponseEntity<Boolean> result = restTemplate.getForEntity(url + "/projectsImport", Boolean.class, entity);
        return result.getBody();

    }

    @Override
    public boolean saveDevelopersDataFromUploadFile(MultipartFile multipartFile) {

        LOGGER.debug("saveDataFromUploadFile({})",multipartFile.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.MULTIPART_FORM_DATA));
        HttpEntity <MultipartFile> entity = new HttpEntity<>(multipartFile, headers);
        ResponseEntity<Boolean> result = restTemplate.getForEntity(url + "/developersImport", Boolean.class, entity);
        return result.getBody();

    }
}
