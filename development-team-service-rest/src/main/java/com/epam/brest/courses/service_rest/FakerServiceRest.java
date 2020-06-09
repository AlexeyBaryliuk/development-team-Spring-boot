package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.service.FakerService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SuppressFBWarnings(value = "DLS_DEAD_LOCAL_STORE")
public class FakerServiceRest implements FakerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakerServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public FakerServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public void changeProjectsTestData(String locale, Integer numberOfChanges) {

        LOGGER.debug("changeProjectsTestData({}, {}})",locale,  numberOfChanges);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/projects")
                .queryParam("locale", locale)
                .queryParam("number", numberOfChanges);

        restTemplate.getForObject(builder.toUriString(), String.class);

    }

    @Override
    public void changeDevelopersTestsData(String locale, Integer numberOfChanges) {

        LOGGER.debug("changeDevelopersTestsData({}, {}})",locale,  numberOfChanges);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/developers")
                .queryParam("locale", locale)
                .queryParam("number", numberOfChanges);

        restTemplate.getForObject(builder.toUriString(), String.class);

    }

    @Override
    public List<String> findAllFakerLocale() {
        LOGGER.debug("findAll()");

        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url,List.class);
        return  responseEntity.getBody();
    }
}
