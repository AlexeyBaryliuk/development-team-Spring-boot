package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.service.FakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

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

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("locale", locale)
                .queryParam("number", numberOfChanges);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,entity, String.class);

    }

    @Override
    public void changeDevelopersTestsData(String locale, Integer numberOfChanges) {

    }
}
