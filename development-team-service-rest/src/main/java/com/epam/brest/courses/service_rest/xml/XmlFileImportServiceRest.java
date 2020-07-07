package com.epam.brest.courses.service_rest.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.xml.XmlFileImportService;
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

import java.util.List;

public class XmlFileImportServiceRest implements XmlFileImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileImportService.class);

    private  final String url;

    private final RestTemplate restTemplate;

    public XmlFileImportServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Projects> parseProjectsXMLFile(MultipartFile multipartFile) {

        LOGGER.debug("parseProjectsXMLFile({})",multipartFile.getOriginalFilename());

        Resource resource = multipartFile.getResource();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ResponseEntity<List> result = restTemplate.postForEntity(url + "/projects/import/xml", requestEntity, List.class);

        return (List<Projects>)result.getBody();
    }

    @Override
    public List<Developers> parseDevelopersXMLFile(MultipartFile multipartFile) {
        LOGGER.debug("parseProjectsXMLFile({})",multipartFile.getOriginalFilename());

        Resource resource = multipartFile.getResource();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ResponseEntity<List> result = restTemplate.postForEntity(url + "/developers/import/xml", requestEntity, List.class);

        return (List<Developers>)result.getBody();
    }
}
