package com.epam.brest.courses.soapService;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import com.epam.brest.courses.service.Projects_DevelopersService;
import com.epam.brest.courses.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.utils.DevelopersUtils.convertDeveloperInfoToDevelopers;

public class Projects_DevelopersClient extends WebServiceGatewaySupport
        implements Projects_DevelopersService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects_DevelopersClient.class);

    @Override
    public List<Developers> selectDevelopersFromProjects_Developers(Integer projectId) {

        LOGGER.debug("SoapService - electDevelopersFromProjects_Developers");
        SelectDevelopersFromProjectsDevelopersRequest request = new SelectDevelopersFromProjectsDevelopersRequest();
        request.setProjectId(projectId);

        SelectDevelopersFromProjectsDevelopersResponse response = (SelectDevelopersFromProjectsDevelopersResponse)getWebServiceTemplate()
                .marshalSendAndReceive(request);
        List<Developers> developersList = new ArrayList<>();

        for (int i = 0; i < response.getListOfDevelopersInfo().size(); i++) {
            developersList.add(convertDeveloperInfoToDevelopers(response.getListOfDevelopersInfo().get(i)));

        }
        return developersList;
    }

    @Override
    public Integer addDeveloperToProjects_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("SoapService - addDeveloperToProjects_DevelopersRequest");
        AddDeveloperToProjectsDevelopersRequest request = new AddDeveloperToProjectsDevelopersRequest();
        request.setProjectId(projectId);
        request.setDeveloperId(developerId);

        AddDeveloperToProjectsDevelopersResponse response = (AddDeveloperToProjectsDevelopersResponse)getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response.getAddToProjectsDevelopers();
    }

    @Override
    public Integer deleteDeveloperFromProject_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("SoapService - deleteDeveloperFromProjects_DevelopersRequest");
        DeleteDeveloperFromProjectsDevelopersRequest request = new DeleteDeveloperFromProjectsDevelopersRequest();
        request.setProjectId(projectId);
        request.setDeveloperId(developerId);

        DeleteDeveloperFromProjectsDevelopersResponse response = (DeleteDeveloperFromProjectsDevelopersResponse)getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response.getDeleteFromProjectsDevelopers();
    }

    @Override
    public Optional<Projects_Developers> findByIdFromProjects_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("SoapService - findByIdFromProjects_DevelopersRequest");
        FindByIdFromProjectsDevelopersRequest request = new FindByIdFromProjectsDevelopersRequest();
        request.setProjectId(projectId);
        request.setDeveloperId(developerId);

        FindByIdFromProjectsDevelopersResponse response = (FindByIdFromProjectsDevelopersResponse)getWebServiceTemplate()
                .marshalSendAndReceive(request);
        Projects_Developers projects_developers = new Projects_Developers();
        projects_developers.setDeveloperId(response.getProjectsDevelopersInfo().getDeveloperId());
        projects_developers.setProjectId(response.getProjectsDevelopersInfo().getProjectId());

        return Optional.of(projects_developers);
    }
}

