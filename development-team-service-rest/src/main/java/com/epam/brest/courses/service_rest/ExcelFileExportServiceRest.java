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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;

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
        LOGGER.debug("exportProjectsToExcel({})", projectsList);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        ResponseEntity<byte[]> response = restTemplate.exchange(url + "/projectsDownload",
                GET,
                new HttpEntity<>(headers),
                byte[].class);

        return new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public ByteArrayInputStream exportDevelopersToExcel(List<Developers> developersList) {

        LOGGER.debug("exportProjectsToExcel({})", developersList);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        ResponseEntity<byte[]> response = restTemplate.exchange(url + "/developersDownload",
                GET,
                new HttpEntity<>(headers),
                byte[].class);

        return new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
    }
}