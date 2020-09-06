package com.epam.brest.courses.soapService;


import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.utils.ProjectsUtils.convertProjectInfoToProjects;
import static com.epam.brest.courses.utils.ProjectsUtils.convertProjectsToProjectInfo;

public class ProjectsClient extends WebServiceGatewaySupport
        implements ProjectsService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsClient.class);

    @Override
    public List<Projects> findAll() {

        LOGGER.debug("Client - find all.");
        GetAllProjectsRequest getAllProjectsRequest =new GetAllProjectsRequest();

        GetAllProjectsResponse getAllProjectsResponse = (GetAllProjectsResponse)getWebServiceTemplate()
                .marshalSendAndReceive(getAllProjectsRequest);

        List<ProjectInfo> projectInfoList = getAllProjectsResponse.getProjectInfo();
        List<Projects> projects = new ArrayList<>();

        for (int i = 0; i < projectInfoList.size(); i++) {
            Projects project = new Projects();
            project.setProjectId(projectInfoList.get(i).getProjectId());
            project.setDescription(projectInfoList.get(i).getDescription());
            project.setDateAdded(LocalDate.parse(projectInfoList.get(i).getDateAdded().toXMLFormat()));
            projects.add(project);
        }

        return projects;
    }

    @Override
    public Optional<Projects> findByProjectId(Integer projectId) {

        LOGGER.debug("SoapService - findByProjectId");
        FindByProjectIdRequest findByDeveloperIdRequest = new FindByProjectIdRequest();

        findByDeveloperIdRequest.setProjectId(projectId);
        FindByProjectIdResponse findByDeveloperIdResponse = (FindByProjectIdResponse)getWebServiceTemplate()
                .marshalSendAndReceive(findByDeveloperIdRequest);

        return Optional.of(convertProjectInfoToProjects(findByDeveloperIdResponse.getProjectInfo()));
    }

    @Override
    public Integer update(Projects project) throws DatatypeConfigurationException {

        LOGGER.debug("SoapService - updateProject");
        UpdateProjectRequest updateProjectRequest = new UpdateProjectRequest();
        updateProjectRequest.setProjectInfo(convertProjectsToProjectInfo(project));

        UpdateProjectResponse updateProjectResponse = (UpdateProjectResponse)getWebServiceTemplate()
                .marshalSendAndReceive(updateProjectRequest);

        return updateProjectResponse.getUpdatedProject();
    }

    @Override
    public Integer create(Projects project) throws DatatypeConfigurationException {

        LOGGER.debug("SoapService - createProject");
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setProjectInfo(convertProjectsToProjectInfo(project));

        CreateProjectResponse createProjectResponse = (CreateProjectResponse)getWebServiceTemplate()
                .marshalSendAndReceive(createProjectRequest);

        return createProjectResponse.getProjectCreate();
    }

    @Override
    public Integer createF(Projects project) throws DatatypeConfigurationException {

        LOGGER.debug("SoapService - createProject");
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setProjectInfo(convertProjectsToProjectInfo(project));

        CreateProjectResponse createProjectResponse = (CreateProjectResponse)getWebServiceTemplate()
                .marshalSendAndReceive(createProjectRequest);

        return createProjectResponse.getProjectCreate();
    }

    @Override
    public Integer delete(Integer projectId) {

        LOGGER.debug("SoapService - deleteProject");
        DeleteProjectRequest deleteProjectRequest = new DeleteProjectRequest();
        deleteProjectRequest.setProjectId(projectId);

        DeleteProjectResponse deleteProjectResponse = (DeleteProjectResponse)getWebServiceTemplate()
                .marshalSendAndReceive(deleteProjectRequest);

        return deleteProjectResponse.getProjectDelete();
    }
}


