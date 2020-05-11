package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import com.epam.brest.courses.service.Projects_DevelopersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Projects_DevelopersServiceRest implements Projects_DevelopersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects_DevelopersServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public Projects_DevelopersServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Developers> selectDevelopersFromProjects_Developers(Integer projectId) {

        LOGGER.debug("selectDevelopersFromProjects_Developers({}) ", projectId);

        ResponseEntity <List> responseEntity = restTemplate.getForEntity(url + "/" + projectId, List.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer addDeveloperToProjects_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("SERVICE REST*******addDeveloperToProjects_Developers({}, {})",projectId, developerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Projects_Developers> entity = new HttpEntity<>( headers);
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url + "/" + projectId + "/" + developerId
                , HttpMethod.POST,entity,Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer deleteDeveloperFromProject_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("deleteDeveloperFromProject_Developers({}, {})",projectId, developerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Projects_Developers> entity = new HttpEntity<>( headers);

        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url + "/" +projectId + "/" + developerId
                , HttpMethod.DELETE,entity,Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public Optional<Projects_Developers> findByIdFromProjects_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("______findByIdFromProjects_Developers({}, {})",projectId, developerId);

        ResponseEntity<Projects_Developers> responseEntity =
                restTemplate.getForEntity(url + "/" + projectId + "/" + developerId, Projects_Developers.class);

        return  Optional.ofNullable(responseEntity.getBody());
    }
}
