package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.POST;

public class ExcelFileExportServiceRest implements ExcelFileExportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileExportServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ExcelFileExportServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public ByteArrayInputStream exportProjectsToExcel(List<Projects> projectsList) throws IOException {
        LOGGER.debug("***************************************exportProjectsToExcel({})", projectsList);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<List<Projects>> entity = new HttpEntity<>(projectsList, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url + "/projectsDownload",
                POST,
                entity,
                byte[].class);

        return new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public ByteArrayInputStream exportDevelopersToExcel(List<Developers> developersList) {

        LOGGER.debug("exportProjectsToExcel({})", developersList);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<List<Developers>> entity = new HttpEntity<>(developersList, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url + "/developersDownload",
                POST,
                entity,
                byte[].class);

        return new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
    }
}