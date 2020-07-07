package com.epam.brest.courses.rest_app.controllers;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.xml.XmlFileExportService;
import com.epam.brest.courses.service.xml.XmlFileImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class XmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlController.class);

    private final XmlFileExportService xmlFileExportService;

    private final XmlFileImportService xmlFileImportService;

    public XmlController(XmlFileExportService xmlFileExportService, XmlFileImportService xmlFileImportService) {
        this.xmlFileExportService = xmlFileExportService;
        this.xmlFileImportService = xmlFileImportService;
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

    @PostMapping(value = "/projects/import/xml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Projects> importXmlToProjects(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        LOGGER.debug("MultipartFile for projects = {}", multipartFile.getOriginalFilename());

        return xmlFileImportService.parseProjectsXMLFile(multipartFile);
    }

    @PostMapping(value = "/developers/import/xml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Developers> importXmlToDevelopers(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        LOGGER.debug("MultipartFile for projects = {}", multipartFile.getOriginalFilename());

        return xmlFileImportService.parseDevelopersXMLFile(multipartFile);
    }
}
