package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class ProjectsDtoServiceRest implements ProjectsDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ProjectsDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ProjectsDto> findBetweenDates(LocalDate dateStart, LocalDate dateEnd) {

        LOGGER.debug("findBetweenDates({}, {}})",dateStart,  dateEnd);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("dateStart", dateStart)
                .queryParam("dateEnd", dateEnd);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        return  response.getBody();
    }

    @Override
    public List<ProjectsDto> countOfDevelopers() {

        LOGGER.debug("countOfDevelopers()");

        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url + "/findAll", List.class);
        return responseEntity.getBody();
    }
}
