package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.dto.ProjectsDto;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(classes={TestConfig.class} )
@ExtendWith(SpringExtension.class)
class ProjectsDtoServiceRestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsDtoServiceRestIT.class);

    public static final String PROJECTS_DTO_URL = "http://localhost:8088/projectsDto";

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ProjectsDtoServiceRest projectsDtoServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        projectsDtoServiceRest = new ProjectsDtoServiceRest(PROJECTS_DTO_URL, restTemplate);
    }

    @Test
    void shouldFindBetweenDates() throws Exception {

        LOGGER.debug("shouldFindBetweenDates()");
    //given
        LocalDate dateStart = LocalDate.now().minusDays(1);
        LocalDate dateEnd = LocalDate.now().plusDays(1);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_DTO_URL + "?dateStart=" + dateStart + "&dateEnd=" + dateEnd)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(save(0), save(1))))
                );
        //when
        List<ProjectsDto> projectsDtoList = projectsDtoServiceRest.findBetweenDates(dateStart,dateEnd);

        //then
        assertTrue(projectsDtoList.size() == 2);
    }

    @Test
    void shouldCountOfDevelopers() throws Exception {

        LOGGER.debug("shouldCountOfDevelopers()");
        //given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_DTO_URL + "/findAll")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(save(0), save(1))))
                );
        //when
        List<ProjectsDto> projectsDtoList = projectsDtoServiceRest.countOfDevelopers();

        //then
        assertTrue(projectsDtoList.size() > 0);
    }

    private ProjectsDto save(int index){

        ProjectsDto projectsDto = new ProjectsDto();
        projectsDto.setProjectId(index);
        projectsDto.setDescription("description"+index);
        projectsDto.setDateAdded(LocalDate.now());
        projectsDto.setCountOfDevelopers(1+index);
        return projectsDto;
    }
}