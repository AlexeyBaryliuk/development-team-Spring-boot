package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.web_app.config.viewConfig.ViewConfig;
import com.epam.brest.courses.web_app.testConfig.TestConfig;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@EnableWebMvc
@SpringBootTest(classes={TestConfig.class, ViewConfig.class} )
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class ProjectsControllerIT {


    private final String COMMON_PROJECTS_URL = "/projects";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
     void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void shouldReturnProjectsPage() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_PROJECTS_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("projects"))
                .andExpect(model().attribute("projects", hasItem(
                        allOf(
                                hasProperty("projectId", is(1))
                                , hasProperty("description", is("Create a web application based on SpringJDBC"))
                                , hasProperty("countOfDevelopers", is(Integer.valueOf(2)))
                                , hasProperty("dateAdded", is(convertToLocalDate("2019-07-15")))
                        )
                )))
                .andExpect(model().attribute("projects", hasItem(
                        allOf(
                                hasProperty("projectId", is(2))
                                , hasProperty("description", is("Create a web application based on SpringBoot"))
                                , hasProperty("countOfDevelopers", is(Integer.valueOf(1)))
                                , hasProperty("dateAdded", is(convertToLocalDate("2019-08-13")))
                        )
                )))
                .andExpect(model().attribute("projects", hasItem(
                        allOf(
                                hasProperty("projectId", is(3))
                                , hasProperty("description", is("Create a web application based on Hibernate"))
                                , hasProperty("countOfDevelopers", is(Integer.valueOf(1)))
                                , hasProperty("dateAdded", is(convertToLocalDate("2020-01-17")))
                        )
                )));
    }

    @Test
    public void shouldReturnProjectsBetweenDatesPage() throws Exception {

        //given
        LocalDate dateStart = LocalDate.now().minusYears(10);
        LocalDate dateEnd = LocalDate.now().plusDays(1);

        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_PROJECTS_URL)
                        .param("dateStart", dateStart.toString())
                        .param("dateEnd", dateEnd.toString())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("projects"))
                .andExpect(model().attribute("projects", hasItem(
                        allOf(
                                hasProperty("projectId", is(3))
                                , hasProperty("description", is("Create a web application based on Hibernate"))
                                , hasProperty("countOfDevelopers", is(Integer.valueOf(1)))
                                , hasProperty("dateAdded", is(convertToLocalDate("2020-01-17")))
                        )
                )));
    }

    @Test
    public void shouldReturnToProjectsPageIfProjectNotFoundById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/projects/99999")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/projects"));
    }

    @Test
    public void shouldUpdateProjectAfterEdit() throws Exception {

        Projects project = new Projects();
                project.setProjectId(1);
                project.setDescription("test");

        mockMvc.perform(
                MockMvcRequestBuilders.post(COMMON_PROJECTS_URL + "/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("projectId", project.getProjectId().toString())
                        .param("description", project.getDescription())
                        .param("dateAdded", project.getDateAdded().toString())
                        .sessionAttr("project", project)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COMMON_PROJECTS_URL))
                .andExpect(redirectedUrl(COMMON_PROJECTS_URL));
    }

    @Test
    public void shouldRejectUpdateDepartmentOnLargeDepartmentName() throws Exception {

        Projects project = new Projects();
                project.setProjectId(1);
                project.setDescription(RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE + 1));


        mockMvc.perform(
                MockMvcRequestBuilders.post(COMMON_PROJECTS_URL + "/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("projectId", project.getProjectId().toString())
                        .param("description", project.getDescription())
                        .param("dateAdded", project.getDateAdded().toString())
                        .sessionAttr("project", project)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("project"));
    }

    @Test
    public void shouldOpenNewProjectPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_PROJECTS_URL + "/add")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("projectAdd"))
                .andExpect(model().attribute("project", isA(Projects.class)))
//                .andExpect(model().attribute("developerEntity", isA(Developers.class)))
        ;
    }


    @Test
    public void shouldAddNewProject() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post(COMMON_PROJECTS_URL + "/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "test")
        ).andExpect(status().isFound())
                .andExpect(redirectedUrl(COMMON_PROJECTS_URL));
    }

    @Test
    public void shouldRejectAddProjectOnLargeDescription() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post(COMMON_PROJECTS_URL + "/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", RandomStringUtils.randomAlphabetic(PROJECT_DESCRIPTION_SIZE + 1))
        ).andExpect(status().isOk())
                .andExpect(view().name("projectAdd"));
    }

    @Test
    public void shouldDeleteProject() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_PROJECTS_URL + "/3/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COMMON_PROJECTS_URL))
                .andExpect(redirectedUrl(COMMON_PROJECTS_URL));
    }

    @Test
    public void shouldDeleteDeveloperFromProjects_developers() throws Exception {

        String projectId = "/3";
        String developerId = "/1";
        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_PROJECTS_URL + projectId + developerId + "/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COMMON_PROJECTS_URL +projectId))
                .andExpect(redirectedUrl(COMMON_PROJECTS_URL + projectId));
    }

    private LocalDate convertToLocalDate(String dateAdded) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateForId = LocalDate.parse(dateAdded, dtf);
        return localDateForId;
    }

}