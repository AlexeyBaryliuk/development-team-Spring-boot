package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.rest_app.controllers.ProjectsController;
import com.epam.brest.courses.rest_app.exception.ErrorResponse;
import com.epam.brest.courses.rest_app.exception.CustomExceptionHandler;
import com.epam.brest.courses.rest_app.testConfig.TestConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static com.epam.brest.courses.rest_app.exception.CustomExceptionHandler.PROJECT_NOT_FOUND;
import static com.epam.brest.courses.rest_app.exception.CustomExceptionHandler.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={ProjectsController.class, TestConfig.class} )
@TestPropertySource("classpath:sql-development-team.properties")
class ProjectsControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsControllerIT.class);

    public static final String PROJECTS_ENDPOINT = "/projects";

    @Autowired
    private ProjectsController projectsController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcProjectsService projectsService = new MockMvcProjectsService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectsController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllProjects() throws Exception {

        List<Projects> projects = projectsService.findAll();
        assertNotNull(projects);
        assertTrue(projects.size() > 0);
    }

    @Test
    void shouldfindByDeveloperId() throws Exception {

        // given
        Projects project = new Projects();
                project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer id = projectsService.create(project);
        assertNotNull(id);

        Optional<Projects> projectOptional = projectsService.findByDeveloperId(id);
        assertTrue(projectOptional.isPresent());

        projectOptional.get().
                setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));

        // when
        int result = projectsService.update(projectOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Projects> updatedProjectOptional = projectsService.findByDeveloperId(id);
        assertTrue(updatedProjectOptional.isPresent());
        assertEquals(updatedProjectOptional.get().getProjectId(), id);
        assertEquals(updatedProjectOptional.get().getDescription(),projectOptional.get().getDescription());

    }

    @Test
    void shouldCreateProject() throws Exception {

        Projects project = new Projects();
               project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer id = projectsService.create(project);
        assertNotNull(id);

    }

    @Test
    void shouldCreatByDescription() throws Exception {

        // given
        String description = RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE);

        // when
        Integer id = projectsService.createByDescription(description);

        // then
        Optional<Projects> projectsOptionalAfterAdded = projectsService.findByDeveloperId(id);

        assertTrue(projectsOptionalAfterAdded.isPresent());
        assertEquals(id,projectsOptionalAfterAdded.get().getProjectId());
        assertEquals(description,projectsOptionalAfterAdded.get().getDescription());
    }

    @Test
    void shouldUpdateProject() throws Exception {

        // given
        Projects project = new Projects();
               project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer id = projectsService.create(project);
        assertNotNull(id);

        Optional<Projects> projectsOptional = projectsService.findByDeveloperId(id);
        assertTrue(projectsOptional.isPresent());
        projectsOptional.get().setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));

        // when
        Integer result = projectsService.update(projectsOptional.get());
        Optional<Projects> projectsOptionalAfterUpdate = projectsService.findByDeveloperId(id);
        assertTrue(projectsOptionalAfterUpdate.isPresent());

        // then
        assertTrue(1 ==result);
        assertFalse(projectsOptionalAfterUpdate.get().getDescription() == project.getDescription());
    }

    @Test
    void shouldDeleteProject() throws Exception {

        // given
        Projects project = new Projects();
                project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer id = projectsService.create(project);
        assertNotNull(id);

        //when
        Integer result = projectsService.delete(id);
        assertTrue(1 == result);

    }
    @Test
    public void shouldReturnProjectNotFoundError() throws Exception {

        LOGGER.debug("shouldReturnProjectNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(PROJECTS_ENDPOINT + "/999999")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), PROJECT_NOT_FOUND);
    }

    @Test
    public void shouldFailOnCreateProjectWithDuplicateDescription() throws Exception {

        LOGGER.debug("shouldFailOnCreateProjectWithDuplicateDescription()");
        Projects project = new Projects();
                project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer id = projectsService.create(project);
        assertNotNull(id);

        Projects projectNew = new Projects();
                projectNew.setDescription(project.getDescription());

        MockHttpServletResponse response =
                mockMvc.perform(post(PROJECTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectNew))
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();

        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), VALIDATION_ERROR );
    }

    class MockMvcProjectsService {

        public List<Projects> findAll() throws Exception {

            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(PROJECTS_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Projects>>() {
            });
        }


        public Integer create(Projects project) throws Exception {

            LOGGER.debug("create({})", project);
            String json = objectMapper.writeValueAsString(project);
            MockHttpServletResponse response =
                    mockMvc.perform(post(PROJECTS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public Integer createByDescription(String description) throws Exception {

            LOGGER.debug("createByDescription({})", description);
            String json = objectMapper.writeValueAsString(description);
            MockHttpServletResponse response =
                    mockMvc.perform(post(PROJECTS_ENDPOINT + "/addByDescription")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public Optional<Projects> findByDeveloperId(Integer projectId) throws Exception {

            LOGGER.debug("findByDeveloperId({})", projectId);
            MockHttpServletResponse response = mockMvc.perform(get(PROJECTS_ENDPOINT + "/" + projectId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Projects.class));
        }

        private int update(Projects project) throws Exception {

            LOGGER.debug("update({})", project);
            MockHttpServletResponse response =
                    mockMvc.perform(put(PROJECTS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(project))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer projectId) throws Exception {

            LOGGER.debug("delete(id:{})", projectId);
            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(new StringBuilder(PROJECTS_ENDPOINT).append("/delete/").append(projectId)
                            .toString())
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

    }
}