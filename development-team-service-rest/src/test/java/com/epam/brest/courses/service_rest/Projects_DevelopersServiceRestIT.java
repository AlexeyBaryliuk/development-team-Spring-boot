package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import com.epam.brest.courses.service_rest.testConfig.TestConfig;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(classes={TestConfig.class} )
@ExtendWith(SpringExtension.class)
class Projects_DevelopersServiceRestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects_DevelopersServiceRestIT.class);

    private final String PROJECTS_DEVELOPERS_URL = "http://localhost:8088/projects_developers";

    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper  = new ObjectMapper();

    @Autowired
    private Projects_DevelopersServiceRest projects_developersServiceRest;

    @BeforeEach
    private void before(){

        mockServer = MockRestServiceServer.createServer(restTemplate);
        projects_developersServiceRest = new Projects_DevelopersServiceRest(PROJECTS_DEVELOPERS_URL, restTemplate);
    }

    @Test
    void shouldSelectDevelopersFromProjects_Developers() throws Exception{

        LOGGER.debug("shouldSelectDevelopersFromProjects_Developers()");
        Integer id = 1;
        //given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_DEVELOPERS_URL +"/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(Arrays.asList(createDevelopers(2))))
                );
                //when
        List<Developers> developersList = projects_developersServiceRest
                                                  .selectDevelopersFromProjects_Developers(id);

        //then
        mockServer.verify();
        assertTrue(developersList.size() > 0);
    }

    @Test
    void shouldAddDeveloperToProjects_Developers() throws Exception{

        LOGGER.debug("shouldAddDeveloperToProjects_Developers() ");
        Integer projectId = 1;
        Integer developerId = 2;
        //given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_DEVELOPERS_URL + "/" + projectId +"/" + developerId)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(0))
                );
        //when
        int result = projects_developersServiceRest.addDeveloperToProjects_Developers(projectId,developerId);

        //then
        mockServer.verify();
        assertEquals(result,0);
    }

    @Test
    void shouldDeleteDeveloperFromProject_Developers()throws Exception {

        LOGGER.debug("shouldDeleteDeveloperFromProject_Developers()");
        //given
        Integer projectId = 1;
        Integer developerId = 2;
        mockServer.expect(ExpectedCount.once(),requestTo(new URI(PROJECTS_DEVELOPERS_URL + "/" + projectId +"/" + developerId)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1")));
        //when
        Integer result = projects_developersServiceRest.deleteDeveloperFromProject_Developers(projectId,developerId);

        ///then
        mockServer.verify();
        assertTrue(result == 1);

    }

    @Test
    void shouldFindByIdFromProjects_Developers() throws Exception{

        LOGGER.debug("shouldFindByIdFromProjects_Developers()");
        //given
        Integer projectId = 1;
        Integer developerId = 2;
        mockServer.expect(ExpectedCount.once(),requestTo(new URI(PROJECTS_DEVELOPERS_URL + "/" + projectId +"/" + developerId)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(createProjects_Developers(0)))
                );
        //when

        Optional<Projects_Developers> projects_developers = projects_developersServiceRest
                .findByIdFromProjects_Developers(projectId,developerId);

        //then
        mockServer.verify();
        assertTrue(projects_developers.isPresent());
    }

    private Developers createDevelopers(int index){

        Developers developer = new Developers().setDeveloperId(index)
                .setFirstName("F" + index)
                .setLastName("L" + index);
        return developer;
    }

    private Projects_Developers createProjects_Developers(int index){

        Projects_Developers projects_developers = new Projects_Developers()
                .setProjectId(index + 1)
                .setDeveloperId(index + 2);

        return projects_developers;
    }
}