package com.epam.brest.courses.web_app.controllers.work_with_data;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.xml.XmlFileExportService;
import com.epam.brest.courses.web_app.controllers.ProjectsController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class XmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlController.class);

    @Autowired
    private ProjectsController projectsController;

    @Autowired
    private ProjectsService projectsService;

    private final XmlFileExportService xmlFileExportService;


    public XmlController(XmlFileExportService xmlFileExportService) {
        this.xmlFileExportService = xmlFileExportService;
    }

    @GetMapping("/projects/export/xml")
    public String projectsDownloadXml(HttpServletResponse response) throws IOException, XMLStreamException {

        ObjectMapper mapper = new ObjectMapper();
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
}
