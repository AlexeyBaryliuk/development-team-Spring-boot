package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Projects;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(classes={TestConfig.class} )
@ExtendWith(SpringExtension.class)
class ProjectsServiceRestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsServiceRestIT.class);

    public static final String PROJECTS_URL = "http://localhost:8088/projects";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ProjectsServiceRest projectsServiceRest;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        projectsServiceRest = new ProjectsServiceRest(PROJECTS_URL, restTemplate);
    }

    @Test
    void shouldFindAllProjects() throws URISyntaxException, JsonProcessingException {

        LOGGER.debug("shouldFindAllProjects()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Projects> departments = projectsServiceRest.findAll();

        // then
        mockServer.verify();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    @Test
    void shouldFindProjectById() throws Exception {

        LOGGER.debug("shouldFindProjectById() ");
        // given
        Integer id = 1;
        Projects project = create(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(project))
                );

        // when
        Optional<Projects> optionalProject = projectsServiceRest.findByProjectId(id);

        // then
        mockServer.verify();
        assertTrue(optionalProject.isPresent());
        assertEquals(optionalProject.get().getProjectId(), id);
        assertEquals(optionalProject.get().getDescription(), project.getDescription());
    }

    @Test
    void shouldUpdateProject() throws Exception {

        // given
        Integer id = 1;
        Projects project = create(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(project))
                );

        // when
        int result = projectsServiceRest.update(project);
        Optional<Projects> updatedProjectOptional = projectsServiceRest.findByProjectId(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertTrue(updatedProjectOptional.isPresent());
        assertEquals(updatedProjectOptional.get().getProjectId(), id);
        assertEquals(updatedProjectOptional.get().getDescription(), project.getDescription());
    }



    @Test
    void shouldCreateProject() throws Exception {

        // given
        Integer id = 1;
        Projects project = create(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(id))
                );
        //when
        Integer projectId = projectsServiceRest.create(project);

        // then
        mockServer.verify();
        assertNotNull(projectId);
    }

    @Test
    void shouldDelete() throws Exception {

        //given
        Integer id = 1;

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL + "/delete/" +  id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        //when
        int result = projectsServiceRest.delete(id);

        // then
        mockServer.verify();
        assertEquals(result,1);
    }

    private Projects create(int index){

        Projects projects = new Projects();
        projects.setProjectId(index);
        projects.setDescription("description"+index);
        projects.setDateAdded(LocalDate.now());
        return projects;
    }
}