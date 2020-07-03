package com.epam.brest.courses.service_rest.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.xml.XmlFileExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class XmlFileExportServiceRest implements XmlFileExportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileExportServiceRest.class);

    private  final String url;

    private final RestTemplate restTemplate;

    public XmlFileExportServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public void exportProjectsToXml(List<Projects> projectsList) throws IOException, XMLStreamException {

        LOGGER.debug("exportProjectsToXml({})", projectsList);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List<Projects>> entity = new HttpEntity<>(projectsList, headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, ResponseEntity.class);

    }

    @Override
    public void exportDevelopersToXml(List<Developers> developersList) {

    }
}
