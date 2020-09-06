package com.epam.brest.courses.soap_service_api;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.utils.DevelopersUtils.convertDeveloperInfoToDevelopers;
import static com.epam.brest.courses.utils.DevelopersUtils.convertDevelopersToDevelopersInfo;

public class DevelopersClient extends WebServiceGatewaySupport implements DevelopersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersClient.class);

    @Override
    public List<Developers> findAll() {

        LOGGER.debug("SoapService - getAllDevelopers");
        FindAllDevelopersRequest findAllDevelopersRequest = new FindAllDevelopersRequest();

        FindAllDevelopersResponse findAllDevelopersResponse = (FindAllDevelopersResponse)getWebServiceTemplate()
                .marshalSendAndReceive(findAllDevelopersRequest);
        List<Developers> developersList = new ArrayList<>();

        List<DeveloperInfo> developerInfos = findAllDevelopersResponse.getDeveloperInfo();

        for (int i = 0; i < developerInfos.size(); i++) {
            developersList.add(convertDeveloperInfoToDevelopers(developerInfos.get(i)));
        }
        return developersList;
    }

    @Override
    public Optional<Developers> findByDeveloperId(Integer developerId) {

        LOGGER.debug("SoapService - findByDeveloperId");
        FindDevByDeveloperIdRequest findDevByDeveloperIdRequest= new FindDevByDeveloperIdRequest();
        findDevByDeveloperIdRequest.setDeveloperId(developerId);

        FindDevByDeveloperIdResponse findDevByDeveloperIdResponse = (FindDevByDeveloperIdResponse)getWebServiceTemplate()
                .marshalSendAndReceive(findDevByDeveloperIdRequest);

        return Optional.of( convertDeveloperInfoToDevelopers(findDevByDeveloperIdResponse.getDeveloperInfo()));
    }

    @Override
    public Integer create(Developers developer) {

        LOGGER.debug("SoapService - createDeveloper");
        CreateDeveloperRequest createDeveloperRequest= new CreateDeveloperRequest();
        createDeveloperRequest.setDeveloperInfo(convertDevelopersToDevelopersInfo(developer));

        CreateDeveloperResponse createDeveloperResponse = (CreateDeveloperResponse)getWebServiceTemplate()
            .marshalSendAndReceive(createDeveloperRequest);

        return createDeveloperResponse.getDeveloperCreate();
    }

    @Override
    public Integer update(Developers developer) {

        LOGGER.debug("SoapService - updateDeveloper");
        UpdateDeveloperRequest updateDeveloperRequest = new UpdateDeveloperRequest();
        updateDeveloperRequest.setDeveloperInfo(convertDevelopersToDevelopersInfo(developer));

        UpdateProjectResponse updateProjectResponse = (UpdateProjectResponse)getWebServiceTemplate()
                .marshalSendAndReceive(updateDeveloperRequest);

        return updateProjectResponse.getUpdatedProject();
    }

    @Override
    public Integer deleteByDeveloperId(Integer developerId) {

        LOGGER.debug("SoapService - deleteDeveloper");
        DeleteDeveloperRequest deleteDeveloperRequest = new DeleteDeveloperRequest();
        deleteDeveloperRequest.setDeveloperId(developerId);

        DeleteDeveloperResponse deleteDeveloperResponse = (DeleteDeveloperResponse)getWebServiceTemplate()
                .marshalSendAndReceive(deleteDeveloperRequest);

        return deleteDeveloperResponse.getDeveloperDelete();
    }
}
