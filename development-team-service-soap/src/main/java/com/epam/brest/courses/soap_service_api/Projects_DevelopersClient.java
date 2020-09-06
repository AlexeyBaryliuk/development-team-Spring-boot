package com.epam.brest.courses.soap_service_api;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import com.epam.brest.courses.service.Projects_DevelopersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.List;
import java.util.Optional;

public class Projects_DevelopersClient extends WebServiceGatewaySupport implements Projects_DevelopersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects_DevelopersClient.class);

    @Override
    public List<Developers> selectDevelopersFromProjects_Developers(Integer projectId) {
        return null;
    }

    @Override
    public Integer addDeveloperToProjects_Developers(Integer projectId, Integer developerId) {
        return null;
    }

    @Override
    public Integer deleteDeveloperFromProject_Developers(Integer projectId, Integer developerId) {
        return null;
    }

    @Override
    public Optional<Projects_Developers> findByIdFromProjects_Developers(Integer projectId, Integer developerId) {
        return Optional.empty();
    }
}
