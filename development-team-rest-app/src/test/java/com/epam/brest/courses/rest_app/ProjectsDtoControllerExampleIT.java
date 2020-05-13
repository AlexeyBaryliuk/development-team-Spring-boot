package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class ProjectsDtoControllerExampleIT {

    private  static final Logger LOGGER = LoggerFactory.getLogger(ProjectsDtoControllerExampleIT.class);

    @InjectMocks
    private ProjectsDtoController projectsDtoController;

    @Mock
    private ProjectsDtoService projectsDtoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectsDtoController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(projectsDtoService);
    }

    @Test
    public void shouldFindAll() throws Exception {

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateAdded = localDate.format(formatter);

        LOGGER.debug("DateAdded = {}", dateAdded);
        Mockito.when(projectsDtoService.countOfDevelopers()).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/projectsDto/findAll")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].projectId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is("description0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateAdded", Matchers.is(dateAdded)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].countOfDevelopers", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].projectId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", Matchers.is("description1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dateAdded", Matchers.is(dateAdded)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].countOfDevelopers", Matchers.is(1)))
        ;

        Mockito.verify(projectsDtoService).countOfDevelopers();
    }

    private ProjectsDto create(int index){

        ProjectsDto projectsDto = new ProjectsDto();
        projectsDto.setProjectId(index);
        projectsDto.setCountOfDevelopers(0+index);
        projectsDto.setDescription("description"+index);
        projectsDto.setDateAdded(LocalDate.now());
     return projectsDto;
    }
}
