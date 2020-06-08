package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service_rest.testConfig.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(classes={TestConfig.class} )
@ExtendWith(SpringExtension.class)
class FakerServiceRestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakerServiceRestIT.class);

    public static final String FAKER_URL = "http://localhost:8088/changeTestData/projects";

    @Autowired
    private ProjectsServiceRest projectsServiceRest;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private FakerServiceRest fakerServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        fakerServiceRest = new FakerServiceRest(FAKER_URL, restTemplate);
    }

    @Test
    void shouldChangeProjectsTestData() throws URISyntaxException, JsonProcessingException {

        LOGGER.debug("shouldChangeProjectsTestData()");
        //given
        String locale = "ru";
        Integer numberOfChange = 2;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(FAKER_URL
                + "?locale=" + locale
                + "&number=" + numberOfChange)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                );


        //when
       fakerServiceRest.changeProjectsTestData(locale, numberOfChange);

    }

    @Test
    void changeDevelopersTestsData() {
    }
}