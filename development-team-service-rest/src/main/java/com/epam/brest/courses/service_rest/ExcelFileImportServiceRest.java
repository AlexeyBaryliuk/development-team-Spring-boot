package com.epam.brest.courses.service_rest;


import com.epam.brest.courses.service.excel.ExcelFileImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

        LOGGER.debug("saveDataFromUploadFile({})",multipartFile.getSize());

        Resource resource = multipartFile.getResource();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ResponseEntity<Boolean> result = restTemplate.postForEntity(url + "/projectsImport", requestEntity, Boolean.class);

        return result.getBody();
    }

    @Override
    public boolean saveDevelopersDataFromUploadFile(MultipartFile multipartFile) {

        LOGGER.debug("saveDataFromUploadFile({})",multipartFile.toString());

        Resource resource = multipartFile.getResource();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ResponseEntity<Boolean> result = restTemplate.postForEntity(url + "/developersImport", requestEntity, Boolean.class);

        return result.getBody();

    }
}
