package com.epam.brest.courses.endpoints;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.soap_api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

import static com.epam.brest.courses.utils.DevelopersUtils.convertDeveloperInfoToDevelopers;
import static com.epam.brest.courses.utils.DevelopersUtils.convertDevelopersToDevelopersInfo;

@Profile("soap")
@Endpoint
public class DevelopersEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersEndpoint.class);
    @Autowired
    private DevelopersService developersService;

    public DevelopersEndpoint(DevelopersService developersService) {
        this.developersService = developersService;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "findAllDevelopersRequest")
    @ResponsePayload
    public FindAllDevelopersResponse getAllDevelopers(@RequestPayload FindAllDevelopersRequest findAllDevelopersRequest) {

        LOGGER.debug("Endpoint - getAllDevelopers");
        FindAllDevelopersResponse findAllDevelopersResponse = new FindAllDevelopersResponse();

        List<DeveloperInfo> developerInfos = new ArrayList<>();
        List<Developers> developersList = developersService.findAll();

        for (int i = 0; i < developersList.size(); i++) {

            developerInfos.add(convertDevelopersToDevelopersInfo(developersList.get(i)));
        }
        findAllDevelopersResponse.getDeveloperInfo().addAll(developerInfos);
        return findAllDevelopersResponse;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "findDevByDeveloperIdRequest")
    @ResponsePayload
    public FindDevByDeveloperIdResponse findByDeveloperId(@RequestPayload FindDevByDeveloperIdRequest request){

        LOGGER.debug("Endpoint - findByDeveloperId");
        FindDevByDeveloperIdResponse response = new FindDevByDeveloperIdResponse();

        Integer developerId = request.getDeveloperId();
        Developers developer = developersService.findByDeveloperId(developerId).get();
        response.setDeveloperInfo(convertDevelopersToDevelopersInfo(developer));

        return response;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "createDeveloperRequest")
    @ResponsePayload
    public CreateDeveloperResponse createDeveloper(@RequestPayload CreateDeveloperRequest request){

        LOGGER.debug("Endpoint - createDeveloper");
        CreateDeveloperResponse response = new CreateDeveloperResponse();
        DeveloperInfo developerInfo = request.getDeveloperInfo();
        Integer result =  developersService.create(convertDeveloperInfoToDevelopers(developerInfo));
        response.setDeveloperCreate(result);

        return response;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "updateDeveloperRequest")
    @ResponsePayload
    public UpdateDeveloperResponse updateDeveloper(@RequestPayload UpdateDeveloperRequest request){

        LOGGER.debug("Endpoint - updateDeveloper");
        UpdateDeveloperResponse response = new UpdateDeveloperResponse();

        DeveloperInfo developerInfo = request.getDeveloperInfo();
        Integer result = developersService.update(convertDeveloperInfoToDevelopers(developerInfo));

        response.setUpdatedDeveloper(result);

        return response;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "deleteDeveloperRequest")
    @ResponsePayload
    public DeleteDeveloperResponse deleteDeveloper(@RequestPayload DeleteDeveloperRequest request){

        LOGGER.debug("Endpoint - deleteDeveloper");
        DeleteDeveloperResponse response = new DeleteDeveloperResponse();

        Integer developerId = request.getDeveloperId();

        response.setDeveloperDelete(developersService.deleteByDeveloperId(developerId));

        return response;
    }

}
