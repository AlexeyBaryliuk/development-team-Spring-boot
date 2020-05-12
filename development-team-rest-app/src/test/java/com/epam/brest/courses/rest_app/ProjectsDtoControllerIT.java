package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.rest_app.exception.CustomExceptionHandler;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProjectsDtoControllerIT {


    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsDtoControllerIT.class);

    public static final String PROJECTS_ENDPOINT = "/projectsDto";

    @Autowired
    private ProjectsDtoController projectsDtoController;

    @Autowired
    private ProjectsController projectsController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    private MockMvc mockMvcProjectsDto;
    private MockMvc mockMvcProjects;

    ObjectMapper objectMapper = new ObjectMapper();

    MockMvcProjectsDtoService projectsDtoService = new MockMvcProjectsDtoService();
    MockMvcProjectsService projectsService = new MockMvcProjectsService();

    @BeforeEach
    public void before() {
        mockMvcProjectsDto = MockMvcBuilders.standaloneSetup(projectsDtoController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void shouldFindBetweenDates() throws Exception {

        mockMvcProjects = MockMvcBuilders.standaloneSetup(projectsController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();

        LocalDate dateStart = LocalDate.now().minusDays(1);
        LocalDate dateEnd = LocalDate.now().plusDays(1);
        Projects projects = new Projects().setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE));

        Integer id = projectsService.create(projects);
        List<ProjectsDto> projectsDtoList = projectsDtoService.findBetweenDates(dateStart,dateEnd);

        assertTrue(projectsDtoList.size() == 1);
        assertEquals(projects.getDescription(),projectsDtoList.get(0).getDescription() );



    }

    @Test
    void shouldFindAllProjectsDto() throws Exception {

        List<ProjectsDto> projectsDtoList = projectsDtoService.findAll();
        assertNotNull(projectsDtoList);
        assertTrue(projectsDtoList.size() > 0);
    }


    class MockMvcProjectsDtoService{

        public List<ProjectsDto> findBetweenDates(LocalDate dateStart, LocalDate dateEnd) throws Exception {

            LOGGER.debug("PROJECTS_DTO TEST findBetweenDates({}, {})", dateStart, dateEnd);

            MockHttpServletResponse response = mockMvcProjectsDto.perform(get(PROJECTS_ENDPOINT +"?dateStart="+dateStart+"&dateEnd="+dateEnd)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<ProjectsDto>>() {
            });
        }

        public List<ProjectsDto> findAll() throws Exception {

            LOGGER.debug("PROJECTS_DTO TEST findAll()");
            MockHttpServletResponse response = mockMvcProjectsDto.perform(get(PROJECTS_ENDPOINT +"/findAll")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<ProjectsDto>>() {
            });
        }
    }

    class MockMvcProjectsService{
        public Integer create(Projects project) throws Exception {

            LOGGER.debug(" create({})", project);
            String json = objectMapper.writeValueAsString(project);
            MockHttpServletResponse response =
                    mockMvcProjects.perform(post("/projects")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}