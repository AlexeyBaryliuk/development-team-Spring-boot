package com.epam.brest.courses.endpoints;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.soap_api.GetAllProjectsRequest;
import com.epam.brest.courses.soap_api.GetAllProjectsResponse;
import com.epam.brest.courses.soap_api.ProjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.ArrayList;
import java.util.List;

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

        LOGGER.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++Endpoint - getProjectsResponse");
        GetAllProjectsResponse response = new GetAllProjectsResponse();
        List<Projects> projectsList = projectsService.findAll();
        List<ProjectInfo> projectInfos = new ArrayList<>();
        for (int i = 0; i < projectsList.size(); i++) {

            ProjectInfo projectInfo = new ProjectInfo();
            projectInfo.setProjectId(projectsList.get(i).getProjectId());
            projectInfo.setDescription(projectsList.get(i).getDescription());
            projectInfo.setDateAdded(DatatypeFactory
                    .newInstance()
                    .newXMLGregorianCalendar(projectsList.get(i).getDateAdded().toString()));
            projectInfos.add(projectInfo);
        }
        response.getProjectInfo().addAll(projectInfos);
        return response;
    }

}
