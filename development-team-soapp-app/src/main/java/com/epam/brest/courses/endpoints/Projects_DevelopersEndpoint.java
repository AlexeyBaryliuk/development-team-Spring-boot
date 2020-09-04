package com.epam.brest.courses.endpoints;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import com.epam.brest.courses.service.Projects_DevelopersService;
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

import static com.epam.brest.courses.utils.DevelopersUtils.convertDevelopersToDevelopersInfo;

@Profile("soap")
@Endpoint
public class Projects_DevelopersEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects_DevelopersEndpoint.class);

    @Autowired
    private Projects_DevelopersService projects_developersService;


    public Projects_DevelopersEndpoint(Projects_DevelopersService projects_developersService) {
        this.projects_developersService = projects_developersService;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "selectDevelopersFromProjects_DevelopersRequest")
    @ResponsePayload
    public SelectDevelopersFromProjectsDevelopersResponse selectDevelopersFromProjects_Developers
            (@RequestPayload SelectDevelopersFromProjectsDevelopersRequest request){

        LOGGER.debug("Endpoint - electDevelopersFromProjects_Developers");
        SelectDevelopersFromProjectsDevelopersResponse response = new SelectDevelopersFromProjectsDevelopersResponse();

        Integer projectId = request.getProjectId();
        List<Developers> developersList = projects_developersService.selectDevelopersFromProjects_Developers(projectId);

        List<DeveloperInfo> developerInfos = new ArrayList<>();

        for (int i = 0; i < developersList.size(); i++) {

            developerInfos.add(convertDevelopersToDevelopersInfo(developersList.get(i)));
        }

        response.getListOfDevelopersInfo().addAll(developerInfos);
        return response;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "addDeveloperToProjects_DevelopersRequest")
    @ResponsePayload
    public AddDeveloperToProjectsDevelopersResponse addDeveloperToProjects_DevelopersRequest
            (@RequestPayload AddDeveloperToProjectsDevelopersRequest request){

        LOGGER.debug("Endpoint - addDeveloperToProjects_DevelopersRequest");
        AddDeveloperToProjectsDevelopersResponse response = new AddDeveloperToProjectsDevelopersResponse();

        Integer projectId = request.getProjectId();
        Integer developerId = request.getDeveloperId();


        response.setAddToProjectsDevelopers(projects_developersService.addDeveloperToProjects_Developers(projectId,developerId));
        return response;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "deleteDeveloperFromProjects_DevelopersRequest")
    @ResponsePayload
    public DeleteDeveloperFromProjectsDevelopersResponse deleteDeveloperFromProjects_DevelopersRequest
            (@RequestPayload DeleteDeveloperFromProjectsDevelopersRequest request){

        LOGGER.debug("Endpoint - deleteDeveloperFromProjects_DevelopersRequest");
        DeleteDeveloperFromProjectsDevelopersResponse response = new DeleteDeveloperFromProjectsDevelopersResponse();

        Integer projectId = request.getProjectId();
        Integer developerId = request.getDeveloperId();

        response.setDeleteFromProjectsDevelopers(
                projects_developersService.deleteDeveloperFromProject_Developers(projectId, developerId));
        return response;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "findByIdFromProjects_DevelopersRequest")
    @ResponsePayload
    public FindByIdFromProjectsDevelopersResponse findByIdFromProjects_DevelopersRequest
            (@RequestPayload FindByIdFromProjectsDevelopersRequest request){

        LOGGER.debug("Endpoint - findByIdFromProjects_DevelopersRequest");
        FindByIdFromProjectsDevelopersResponse response = new FindByIdFromProjectsDevelopersResponse();

        Integer projectId = request.getProjectId();
        Integer developerId = request.getDeveloperId();

        Projects_Developers projects_developers =
                projects_developersService.findByIdFromProjects_Developers(projectId, developerId).get();

        ProjectsDevelopersInfo projectsDevelopersInfo = new ProjectsDevelopersInfo();

        projectsDevelopersInfo.setDeveloperId(projects_developers.getDeveloperId());
        projectsDevelopersInfo.setProjectId(projects_developers.getProjectId());

        response.setProjectsDevelopersInfo(projectsDevelopersInfo);
        return response;
    }

}
