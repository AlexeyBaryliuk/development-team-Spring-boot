package com.epam.brest.courses.web_app.controllers.web_socket;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.web_app.controllers.ProjectsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private final ProjectsDtoService projectsDtoService;

    public WebSocketController(ProjectsDtoService projectsDtoService) {
        this.projectsDtoService = projectsDtoService;
    }

    @MessageMapping("/chat.getAll")
    @SendTo("/topic/all")
    public List<ProjectsDto> addUser( SimpMessageHeaderAccessor headerAccessor) {

        LOGGER.debug("WebSocket - all.++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    List<ProjectsDto> projectsList = projectsDtoService.countOfDevelopers();
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return projectsList;
    }
}
