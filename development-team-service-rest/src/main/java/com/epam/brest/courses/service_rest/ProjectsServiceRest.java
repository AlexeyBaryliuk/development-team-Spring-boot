package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.ProjectsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProjectsServiceRest implements ProjectsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ProjectsServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Projects> findAll() {

        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Projects>) responseEntity.getBody();
    }

    @Override
    public Optional<Projects> findByDeveloperId(Integer projectId) {

        LOGGER.debug("findByDeveloperId({})", projectId);
        ResponseEntity<Projects> responseEntity =
                restTemplate.getForEntity(url + "/" + projectId, Projects.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Integer update(Projects project) {

        LOGGER.debug("update({})", project);
        // restTemplate.put(url, department);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Projects> entity = new HttpEntity<>(project, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer create(Projects project) {

       LOGGER.debug("create({})", project);
       ResponseEntity responseEntity = restTemplate.postForEntity(url, project,Integer.class);

        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer createF(Projects project) {
        return null;
    }

    @Override
    public Integer delete(Integer projectId) {

        LOGGER.debug("delete({})", projectId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Projects> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/delete/" +  projectId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }
}
