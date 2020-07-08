package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.xml.XmlFileExportService;
import com.epam.brest.courses.service.xml.XmlFileImportService;
import com.epam.brest.courses.web_app.config.viewConfig.ViewConfig;
import com.epam.brest.courses.web_app.testConfig.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@EnableWebMvc
@SpringBootTest(classes={TestConfig.class, ViewConfig.class} )
@TestPropertySource({"classpath:sql-development-team.properties", "classpath:xml.properties"})
class XmlControllerIT {

    private final String COMMON_PROJECTS_URL = "/projects";

    private final String COMMON_DEVELOPERS_URL = "/developers";

    private final String PROJECTS_ZIP_FILE = "src/test/resources/zip/zip_pro/projects_zip.zip";

    private String DEVELOPERS_ZIP_FILE = "src/test/resources/zip/zip_dev/developers_zip.zip";
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
    void shouldReturnProjectsPageAfterExportToXml() throws Exception {

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
    void shouldReturnDevelopersPageAfterExportToXml() throws Exception {

        List<Developers> developersList = new ArrayList<>();
        Developers developer = createDevelopers();
        developersList.add(developer);
        Developers developer1 = createDevelopers();
        developersList.add(developer1);
        Developers developer2 = createDevelopers();
        developersList.add(developer2);

        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_DEVELOPERS_URL+ "/export/xml")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(developersList)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("redirect:" + COMMON_DEVELOPERS_URL))
                .andExpect(redirectedUrl(COMMON_DEVELOPERS_URL));
    }


    @Test
    void shouldReturnProjectsPageAfterImportFromXml() throws Exception {

        File file = new File(PROJECTS_ZIP_FILE);
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        mockMvc.perform(
                multipart(COMMON_PROJECTS_URL + "/import/xml")
                        .file(multipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("redirect:" + COMMON_PROJECTS_URL))
                .andExpect(redirectedUrl(COMMON_PROJECTS_URL));

    }

    @Test
    void shouldReturnDevelopersPageAfterImportFromXml() throws Exception {
        File file = new File(DEVELOPERS_ZIP_FILE);
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        mockMvc.perform(
                multipart(COMMON_DEVELOPERS_URL + "/import/xml")
                        .file(multipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("redirect:" + COMMON_DEVELOPERS_URL))
                .andExpect(redirectedUrl(COMMON_DEVELOPERS_URL));

    }

    public Projects createProjects(){

        Projects project = new Projects();
        project.setDescription("Test");
        project.setProjectId(id);
        id++;
        return project;
    }
    private Developers createDevelopers(){

        Developers developer = new Developers();
        developer.setDeveloperId(id);
        developer.setFirstName("F" + id);
        developer.setLastName("L" + id);
        return developer;
    }
}