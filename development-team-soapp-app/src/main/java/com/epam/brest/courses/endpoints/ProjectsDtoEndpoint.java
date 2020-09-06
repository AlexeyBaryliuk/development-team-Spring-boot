package com.epam.brest.courses.endpoints;

import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.soap_api.CountOfDevelopersRequest;
import com.epam.brest.courses.soap_api.CountOfDevelopersResponse;
import com.epam.brest.courses.soap_api.FindAllByDateAddedBetweenRequest;
import com.epam.brest.courses.soap_api.FindAllByDateAddedBetweenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.util.List;

import static com.epam.brest.courses.utils.ProjectsDtoUtils.convertProjectsListToProjectDtoInfoList;
import static com.epam.brest.courses.utils.ProjectsDtoUtils.convertXMLGregorianCalendarToLocalDate;

@Profile("soap")
@Endpoint
public class ProjectsDtoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsDtoEndpoint.class);

    @Autowired
    private ProjectsDtoService projectsDtoService;


    public ProjectsDtoEndpoint(ProjectsDtoService projectsDtoService) {
        this.projectsDtoService = projectsDtoService;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "findAllByDateAddedBetweenRequest")
    @ResponsePayload
    public FindAllByDateAddedBetweenResponse findAllByDateAddedBetweenRequest
            (@RequestPayload FindAllByDateAddedBetweenRequest request) throws DatatypeConfigurationException {

        LOGGER.debug("Endpoint - findAllByDateAddedBetweenRequest");
        FindAllByDateAddedBetweenResponse response = new FindAllByDateAddedBetweenResponse();

        LocalDate from = convertXMLGregorianCalendarToLocalDate(request.getDateStart());
        LocalDate to = convertXMLGregorianCalendarToLocalDate(request.getDateEnd());

        List<ProjectsDto> projectsList = projectsDtoService.findAllByDateAddedBetween(from, to);

        response.getProjectDtoInfo().addAll(convertProjectsListToProjectDtoInfoList(projectsList));

        return response;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "countOfDevelopersRequest")
    @ResponsePayload
    public CountOfDevelopersResponse countOfDevelopersRequest
            (@RequestPayload CountOfDevelopersRequest request) throws DatatypeConfigurationException {

        LOGGER.debug("Endpoint - countOfDevelopersRequest");
        CountOfDevelopersResponse response = new CountOfDevelopersResponse();

        List<ProjectsDto> projectsDtoList = projectsDtoService.countOfDevelopers();

        response.getProjectDtoInfo().addAll(convertProjectsListToProjectDtoInfoList(projectsDtoList));

        return response;
    }
}