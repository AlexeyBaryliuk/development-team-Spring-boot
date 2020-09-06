package com.epam.brest.courses.soap_service_api;

import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.time.LocalDate;
import java.util.List;

public class ProjectsDtoClient extends WebServiceGatewaySupport implements ProjectsDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsDtoClient.class);

    @Override
    public List<ProjectsDto> findAllByDateAddedBetween(LocalDate dateStart, LocalDate dateEnd) {
        return null;
    }

    @Override
    public List<ProjectsDto> countOfDevelopers() {
        return null;
    }
}
