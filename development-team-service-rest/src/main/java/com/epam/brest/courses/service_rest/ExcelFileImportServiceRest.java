package com.epam.brest.courses.service_rest;


import com.epam.brest.courses.service.ExcelFileImportService;
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
    public boolean saveDataFromUploadFile(MultipartFile multipartFile) {

        LOGGER.debug("saveDataFromUploadFile({})",multipartFile.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
        HttpEntity <MultipartFile> entity = new HttpEntity<>(multipartFile, headers);
        ResponseEntity<Boolean> result = restTemplate.exchange(url + "/projectsImport", HttpMethod.POST, entity, Boolean.class);
        return result.getBody();

    }

    @Override
    public boolean readProjectsDataFromExcel(MultipartFile file) {
        return false;
    }

    @Override
    public boolean readDevelopersDataFromExcel(MultipartFile file) {
        return false;
    }
}
