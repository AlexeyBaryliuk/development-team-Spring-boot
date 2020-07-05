package com.epam.brest.courses.rest_app.controllers;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.xml.XmlFileExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class XmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlController.class);

    private final XmlFileExportService xmlFileExportService;

    public XmlController(XmlFileExportService xmlFileExportService) {
        this.xmlFileExportService = xmlFileExportService;
    }

    @PostMapping(value = "/projects/export/xml" , consumes = "application/json")
    public ResponseEntity exportProjectsToXml(@RequestBody List<Projects> projectsList) throws IOException, XMLStreamException {

        LOGGER.debug("exportProjectsToXml({})", projectsList);
        xmlFileExportService.exportProjectsToXml(projectsList);

        return  new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/developers/export/xml" , consumes = "application/json")
    public ResponseEntity exportDevelopersToXml(@RequestBody List<Developers> developersList) throws IOException, XMLStreamException {

        LOGGER.debug("exportDevelopersToXml({})", developersList);
        xmlFileExportService.exportDevelopersToXml(developersList);

        return  new ResponseEntity(HttpStatus.OK);
    }
}
