package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.xml.XmlFileExportService;
import com.epam.brest.courses.service.xml.XmlFileImportService;
import com.epam.brest.courses.web_app.config.viewConfig.ViewConfig;
import com.epam.brest.courses.web_app.testConfig.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@EnableWebMvc
@SpringBootTest(classes={TestConfig.class, ViewConfig.class} )
@TestPropertySource({"classpath:sql-development-team.properties", "classpath:xml.properties"})
class XmlControllerIT {

    private final String COMMON_PROJECTS_URL = "/projects";

    int id = 1;

    @Autowired
    private WebApplicationContext wac;


    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void projectsExportXml() throws Exception {

        List<Projects> projectsList = new ArrayList<>();
        Projects project = createProjects();
        projectsList.add(project);
        Projects project1 = createProjects();
        projectsList.add(project1);
        Projects project2 = createProjects();
        projectsList.add(project2);

        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_PROJECTS_URL+ "/export/xml")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectsList)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("redirect:" + COMMON_PROJECTS_URL))
                .andExpect(redirectedUrl(COMMON_PROJECTS_URL));
    }

    @Test
    void developersExportXml() {
    }

    @Test
    void projectsImportFile() {
    }

    @Test
    void developersImportFile() {
    }

    public Projects createProjects(){

        Projects project = new Projects();
        project.setDescription("Test");
        project.setProjectId(id);
        id++;
        return project;
    }
}