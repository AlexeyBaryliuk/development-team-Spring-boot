package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.Projects_Developers;

import com.epam.brest.courses.rest_app.controllers.DevelopersController;
import com.epam.brest.courses.rest_app.controllers.ProjectsController;
import com.epam.brest.courses.rest_app.controllers.Projects_DevelopersController;
import com.epam.brest.courses.rest_app.testConfig.TestConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static com.epam.brest.courses.rest_app.DevelopersControllerIT.DEVELOPERS_ENDPOINT;
import static com.epam.brest.courses.rest_app.ProjectsControllerIT.PROJECTS_ENDPOINT;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={Projects_DevelopersController.class
                        , ProjectsController.class
                        , DevelopersController.class
                        , TestConfig.class} )
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class Projects_DevelopersControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects_DevelopersControllerIT.class);

    public static final String PROJECTS_DEVELOPERS_ENDPOINT = "/projects_developers";

    @Autowired
    private Projects_DevelopersController projects_developersController;

    @Autowired
    private ProjectsController projectsController;

    @Autowired
    private DevelopersController developersController;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    private MockMvc mockMvc;

    private MockMvc mockMvcProject;

    private MockMvc mockMvcDeveloper;

    private MockMvcProjects_DevelopersService projects_developersService = new MockMvcProjects_DevelopersService();

    private MockMvcProjectsServicePD projectsService = new MockMvcProjectsServicePD();

    private MockMvcDevelopersServicePD developersService = new MockMvcDevelopersServicePD();

    @BeforeEach
    private void before() {

        mockMvc = MockMvcBuilders.standaloneSetup(projects_developersController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        mockMvcProject = MockMvcBuilders.standaloneSetup(projectsController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        mockMvcDeveloper = MockMvcBuilders.standaloneSetup(developersController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void selectDevelopersFromProjects_Developers() throws Exception {

        LOGGER.debug("selectDevelopersFromProjects_Developers()");
        Projects project = new Projects();
                project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectsService.create(project);
        assertNotNull(projectId);

        Developers developer = new Developers();
                developer.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
                developer.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        Integer developerId = developersService.create(developer);
        assertNotNull(developerId);

            int result = projects_developersService.addDeveloperToProjects_Developers(projectId,developerId);

            assertEquals(1,result);

            List<Developers> developersList = projects_developersService
                                                             .selectDevelopersFromProjects_Developers(projectId);

            assertEquals(1,developersList.size());
            assertEquals(developerId, developersList.get(0).getDeveloperId() );



   }

    @Test
    void addDeveloperToProjects_Developers() throws Exception {

        LOGGER.debug("addDeveloperToProjects_Developers()");
        Projects project = new Projects();
                project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectsService.create(project);
        assertNotNull(projectId);

        Developers developer = new Developers();
                developer.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
                developer.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        Integer developerId = developersService.create(developer);
        assertNotNull(developerId);

        int result = projects_developersService.addDeveloperToProjects_Developers(projectId,developerId);

        assertEquals(1,result);
    }

    @Test
    void deleteDeveloperFromProject_Developers() throws Exception {

        LOGGER.debug("deleteDeveloperFromProject_Developers()");
        Projects project = new Projects();
                project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectsService.create(project);
        assertNotNull(projectId);

        Developers developer = new Developers();
                developer.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
                developer.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        Integer developerId = developersService.create(developer);
        assertNotNull(developerId);
        projects_developersService.addDeveloperToProjects_Developers(projectId,developerId);

        int result = projects_developersService.deleteDeveloperFromProject_Developers(projectId, developerId);

        assertEquals(1,result);

    }

    @Test
    void findByIdFromProjects_Developers() throws Exception {

        LOGGER.debug("findByIdFromProjects_Developers()");
        Projects project = new Projects();
                project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));
        Integer projectId = projectsService.create(project);
        assertNotNull(projectId);

        Developers developer = new Developers();
                developer.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
                developer.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
        Integer developerId = developersService.create(developer);
        assertNotNull(developerId);

        int result = projects_developersService.addDeveloperToProjects_Developers(projectId,developerId);
        assertEquals(1,result);

        Optional<Projects_Developers> optionalDevelopers = projects_developersService.findByIdFromProjects_Developers(projectId, developerId);
        assertTrue(optionalDevelopers.isPresent());
    }

    class MockMvcProjects_DevelopersService {

        public List<Developers> selectDevelopersFromProjects_Developers(Integer projectId) throws Exception {

            LOGGER.debug("selectDevelopersFromProjects_Developers({})", projectId);

            MockHttpServletResponse response = mockMvc.perform(get(PROJECTS_DEVELOPERS_ENDPOINT + "/" + projectId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Developers>>() {
            });
        }

        public int addDeveloperToProjects_Developers(Integer projectId, Integer developerId) throws Exception {

            LOGGER.debug("addDeveloperToProjects_Developers({}, {})", projectId, developerId);

            MockHttpServletResponse response = mockMvc.perform(post(PROJECTS_DEVELOPERS_ENDPOINT + "/" + projectId + "/" + developerId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public int deleteDeveloperFromProject_Developers(Integer projectId, Integer developerId) throws Exception {

            LOGGER.debug("deleteDeveloperFromProject_Developers({}, {})", projectId, developerId);
            MockHttpServletResponse response = mockMvc.perform(delete(PROJECTS_DEVELOPERS_ENDPOINT + "/" + projectId + "/" + developerId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }


        public Optional<Projects_Developers> findByIdFromProjects_Developers(Integer projectId, Integer developerId) throws Exception {

            LOGGER.debug("findByIdFromProjects_Developers({}, {})", projectId, developerId);

            MockHttpServletResponse response = mockMvc.perform(get(PROJECTS_DEVELOPERS_ENDPOINT + "/" + projectId + "/" + developerId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Projects_Developers.class));

        }
    }

    class MockMvcProjectsServicePD {

        public Integer create(Projects project) throws Exception {

            LOGGER.debug("MOCK_MVC from method create({})", project);
            String json = objectMapper.writeValueAsString(project);
            MockHttpServletResponse response =
                    mockMvcProject.perform(post(PROJECTS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

    }

    class MockMvcDevelopersServicePD {

        public Integer create(Developers developer) throws Exception {

            LOGGER.debug("create({})", developer);
            String json = objectMapper.writeValueAsString(developer);
            MockHttpServletResponse response =
                    mockMvcDeveloper.perform(post(DEVELOPERS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

    }
}