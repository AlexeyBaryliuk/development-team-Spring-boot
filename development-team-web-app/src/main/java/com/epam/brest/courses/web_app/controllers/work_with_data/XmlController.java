package com.epam.brest.courses.web_app.controllers.work_with_data;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.xml.XmlFileExportService;
import com.epam.brest.courses.service.xml.XmlFileImportService;
import com.epam.brest.courses.web_app.controllers.ProjectsController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * XmlController.
 */
@Controller
@RequestMapping
@SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC_ANON")
public class XmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlController.class);

    @Autowired
    private ProjectsController projectsController;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private DevelopersService developersService;

    private final XmlFileExportService xmlFileExportService;

    private final XmlFileImportService xmlFileImportService;

    ObjectMapper mapper = new ObjectMapper();

    public XmlController(XmlFileExportService xmlFileExportService, XmlFileImportService xmlFileImportService) {
        this.xmlFileExportService = xmlFileExportService;
        this.xmlFileImportService = xmlFileImportService;
    }

    @GetMapping("/projects/export/xml")
    public String projectsExportXml(HttpServletResponse response) throws IOException, XMLStreamException {

        LOGGER.debug("projectsExportXml()");

        List<ProjectsDto> projectsDtoList = projectsController.getProjectsDtoList();
        System.out.println("++++++++++++++++++++++++++" + projectsDtoList);
        List<Projects> projectsList = new ArrayList<>();

        List<ProjectsDto> projectDtoListMapper = mapper.convertValue(
                projectsDtoList,
                new TypeReference<List<ProjectsDto>>(){}
        );

        for (int i = 0; i < projectsDtoList.size(); i++) {

            Integer projectsDtoId = projectDtoListMapper.get(i).getProjectId();

            Optional<Projects> project = projectsService.findByProjectId(projectsDtoId);
            project.ifPresent(projectsList::add);

        }

        xmlFileExportService.exportProjectsToXml(projectsList);
        return "redirect:/projects";
    }

    @GetMapping("/developers/export/xml")
    public String developersExportXml(HttpServletResponse response) throws IOException, XMLStreamException {

        LOGGER.debug("developersExportXml()");
        List<Developers> developersList = developersService.findAll();

        xmlFileExportService.exportDevelopersToXml(developersList);
        return "redirect:/developers";
    }

    @PostMapping("/projects/import/xml")
    public String projectsImportFile( @RequestParam("file") MultipartFile file){

        LOGGER.debug("projectsImportFile({})", file.getOriginalFilename());
        List <Projects> projectsList = xmlFileImportService.parseProjectsXMLFile(file);

        projectsService.deleteAllProjects();
        List<Projects> projectsListMapper = mapper.convertValue(
                projectsList,
                new TypeReference<List<Projects>>(){}
        );
        for (int i = 0; i < projectsListMapper.size(); i++) {
            projectsService.create(projectsListMapper.get(i));
        }

        return "redirect:/projects";
    }

    @PostMapping("/developers/import/xml")
    public String developersImportFile( @RequestParam("file") MultipartFile file){

        LOGGER.debug("developersImportFile({})", file.getOriginalFilename());
        List <Developers> developersList = xmlFileImportService.parseDevelopersXMLFile(file);

        List<Developers> developersListMapper = mapper.convertValue(
                developersList,
                new TypeReference<List<Developers>>(){}
        );
        developersService.deleteAllDevelopers();

        for (int i = 0; i < developersListMapper.size(); i++) {
            developersService.create(developersListMapper.get(i));
        }

        return "redirect:/developers";
    }
}
