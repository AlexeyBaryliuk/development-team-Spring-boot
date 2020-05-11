package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.DevelopersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class DevelopersServiceRestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersServiceRestIT.class);

    DevelopersServiceRest developersServiceRest;

    private final String DEVELOPEERS_URL = "http://localhost:8088/developers";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    private void before()
    {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        developersServiceRest = new DevelopersServiceRest(DEVELOPEERS_URL,restTemplate);
    }



    @Test
    void shouldFindAllDevelopers()throws Exception {

        LOGGER.debug("shouldFindAllDevelopers()");
        //given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEVELOPEERS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );
        //when
        List<Developers> developersList = developersServiceRest.findAll();

        // then
        mockServer.verify();
        assertNotNull(developersList);
        assertTrue(developersList.size() > 0);
    }

    @Test
    void shouldFindDeveloperById() throws Exception {

        LOGGER.debug("shouldFindDeveloperById()");
        // given
        Integer id = 1;
        Developers developer = create(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEVELOPEERS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(developer))
                );

        // when
        Optional<Developers> optionalDevelopers = developersServiceRest.findById(id);

        // then
        mockServer.verify();
        assertTrue(optionalDevelopers.isPresent());
        assertEquals(optionalDevelopers.get().getDeveloperId(), id);
        assertEquals(optionalDevelopers.get().getFirstName(), developer.getFirstName());
        assertEquals(optionalDevelopers.get().getLastName(), developer.getLastName());
    }

    @Test
    void shouldCreateDeveloper() throws Exception{

        LOGGER.debug("shouldCreateDeveloper()");
        Integer id = 1;
        Developers developer = create(id);
        //given
        mockServer.expect(ExpectedCount.once(),requestTo(new URI(DEVELOPEERS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(id)));


        //when
        Integer developerId = developersServiceRest.create(developer);

        //then
        mockServer.verify();
        assertNotNull(developerId);


    }

    @Test
    void shouldUpdateDeveloper() throws Exception{

        LOGGER.debug("shouldUpdateDeveloper()");
        //given
        Integer id = 1;
        Developers developer = create(id);
        mockServer.expect(ExpectedCount.once(),requestTo(DEVELOPEERS_URL))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(1)));

        //when
        int result = developersServiceRest.update(developer);

        //then
        mockServer.verify();
        assertEquals(result, 1);
    }

    @Test
    void shouldDeleteDeveloper() throws Exception {

        LOGGER.debug("shouldDeleteDeveloper()");
        //given
        Integer id = 1;
        Developers developer = create(id);
        mockServer.expect(ExpectedCount.once(), requestTo(DEVELOPEERS_URL + id ))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString("1")));

        //when
        int result = developersServiceRest.delete(id);

        //then

        assertTrue(result == 1);

    }

    private Developers create(int index){

        Developers developer = new Developers().setDeveloperId(index)
                                               .setFirstName("F" + index)
                                               .setLastName("L" + index);
        return developer;
    }
}