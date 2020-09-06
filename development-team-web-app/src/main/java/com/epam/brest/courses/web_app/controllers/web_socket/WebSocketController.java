package com.epam.brest.courses.web_app.controllers.web_socket;

import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;

@Controller
public class WebSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private final ProjectsDtoService projectsDtoService;

    @Autowired
    private final ProjectsService projectsService;

    public WebSocketController(ProjectsDtoService projectsDtoService, ProjectsService projectsService) {
        this.projectsDtoService = projectsDtoService;
        this.projectsService = projectsService;
    }

    @MessageMapping("/chat.getAll")
    @SendTo("/topic/all")
    public List<ProjectsDto> allProjects( ) throws DatatypeConfigurationException {

    List<ProjectsDto> projectsList = projectsDtoService.countOfDevelopers();

        LOGGER.debug("WebSocket - all.");

        return projectsList;
    }

    @MessageMapping("/delete")
    @SendTo("/topic/delete")
    public String deleteProject(@Payload Integer projectId) {

        LOGGER.debug("WebSocket - delete project with id = {}." , projectId);

        Integer num = projectsService.delete(projectId);

        return num > 0?"delete":"not";
    }
}
