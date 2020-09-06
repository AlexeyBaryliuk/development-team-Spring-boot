package com.epam.brest.courses.endpoints;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.soap_api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.brest.courses.utils.ProjectsUtils.convertProjectInfoToProjects;
import static com.epam.brest.courses.utils.ProjectsUtils.convertProjectsToProjectInfo;

@Profile("soap")
@Endpoint
public class ProjectsEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsEndpoint.class);

    @Autowired
    private ProjectsService projectsService;

    public ProjectsEndpoint(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "getAllProjectsRequest")
    @ResponsePayload
    public GetAllProjectsResponse getAllProjectsResponse(@RequestPayload GetAllProjectsRequest getAllProjectsRequest)
            throws DatatypeConfigurationException, DatatypeConfigurationException {

        LOGGER.debug("Endpoint - getProjectsResponse");
        GetAllProjectsResponse response = new GetAllProjectsResponse();
        List<Projects> projectsList = projectsService.findAll();
        List<ProjectInfo> projectInfos = new ArrayList<>();
        for (int i = 0; i < projectsList.size(); i++) {

            projectInfos.add(convertProjectsToProjectInfo(projectsList.get(i)));
        }
        response.getProjectInfo().addAll(projectInfos);
        return response;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "findByDeveloperIdRequest")
    @ResponsePayload
    public FindByProjectIdResponse findByProjectId(@RequestPayload FindByProjectIdRequest findByDeveloperIdRequest) throws DatatypeConfigurationException {

        LOGGER.debug("Endpoint - findByProjectId");
        FindByProjectIdResponse findByDeveloperIdResponse = new FindByProjectIdResponse();
        Integer projectId = findByDeveloperIdRequest.getProjectId();

        Projects project = projectsService.findByProjectId(projectId).get();

        findByDeveloperIdResponse.setProjectInfo(convertProjectsToProjectInfo(project));
        return findByDeveloperIdResponse;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "updateProjectRequest")
    @ResponsePayload
    public UpdateProjectResponse updateProject(@RequestPayload UpdateProjectRequest updateProjectRequest) throws DatatypeConfigurationException {

        LOGGER.debug("Endpoint - updateProject");
        UpdateProjectResponse updateProjectResponse = new UpdateProjectResponse();
        ProjectInfo projectInfo = updateProjectRequest.getProjectInfo();

        Integer result = projectsService.update(convertProjectInfoToProjects(projectInfo));
        updateProjectResponse.setUpdatedProject(result);

        return updateProjectResponse;

    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "createProjectRequest")
    @ResponsePayload
    public CreateProjectResponse addProject(@RequestPayload CreateProjectRequest createProjectRequest) throws DatatypeConfigurationException {

        LOGGER.debug("Endpoint - createProject");
        CreateProjectResponse createProjectResponse = new CreateProjectResponse();

        ProjectInfo projectInfo = createProjectRequest.getProjectInfo();

        Integer result = projectsService.create(convertProjectInfoToProjects(projectInfo));
        createProjectResponse.setProjectCreate(result);

        return createProjectResponse;
    }

    @PayloadRoot(namespace = "http://epam.com/brest/courses/soap_api",
            localPart = "deleteProjectRequest")
    @ResponsePayload
    public DeleteProjectResponse deleteProject(@RequestPayload DeleteProjectRequest deleteProjectRequest){

        LOGGER.debug("Endpoint - deleteProject");
        DeleteProjectResponse deleteProjectResponse = new DeleteProjectResponse();

        Integer projectId = deleteProjectRequest.getProjectId();

        Integer result = projectsService.delete(projectId);

        deleteProjectResponse.setProjectDelete(result);

        return  deleteProjectResponse;

    }
}


