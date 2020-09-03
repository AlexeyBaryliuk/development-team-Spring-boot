package com.epam.brest.courses.soap_service_api;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.soap_api.GetAllProjectsRequest;
import com.epam.brest.courses.soap_api.GetAllProjectsResponse;
import com.epam.brest.courses.soap_api.ProjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectClient extends WebServiceGatewaySupport implements ProjectsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectClient.class);

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
    public Optional<Projects> findByDeveloperId(Integer projectId) {
        return Optional.empty();
    }

    @Override
    public Integer update(Projects project) {
        return null;
    }

    @Override
    public Integer create(Projects project) {
        return null;
    }

    @Override
    public Integer createF(Projects project) {
        return null;
    }

    @Override
    public Integer delete(Integer projectId) {
        return null;
    }
}
