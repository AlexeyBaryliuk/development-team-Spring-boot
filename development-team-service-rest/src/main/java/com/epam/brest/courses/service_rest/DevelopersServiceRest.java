package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.service.DevelopersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DevelopersServiceRest implements DevelopersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public DevelopersServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }


    @Override
    public List<Developers> findAll() {

        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url,List.class);
        return (List<Developers>) responseEntity.getBody();
    }

    @Override
    public Optional<Developers> findByDeveloperId(Integer developerId) {

        LOGGER.debug("findByDeveloperId({})", developerId);

        ResponseEntity<Developers> responseEntity = restTemplate.getForEntity(url + "/" + developerId, Developers.class);

        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Integer create(Developers developer) {

        LOGGER.debug("create({})", developer);

        ResponseEntity responseEntity = restTemplate.postForEntity(url, developer, Integer.class);

        return (Integer)responseEntity.getBody();
    }

    @Override
    public Integer update(Developers developer) {

        LOGGER.debug("update({})", developer);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Developers> entity = new HttpEntity<>(developer,headers);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity <Integer> responseEntity = restTemplate.exchange(url , HttpMethod.PUT, entity,Integer.class );

        return responseEntity.getBody();
    }

    @Override
    public Integer delete(Integer developerId) {

        LOGGER.debug("delete()");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Integer> entity = new HttpEntity<>(developerId,headers);
        ResponseEntity responseEntity = restTemplate.exchange(url +"/" + developerId, HttpMethod.DELETE,entity,Integer.class);

        return (Integer) responseEntity.getBody();
    }
}
