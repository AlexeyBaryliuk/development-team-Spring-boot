package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.rest_app.controllers.ExcelController;
import com.epam.brest.courses.rest_app.controllers.XmlController;
import com.epam.brest.courses.rest_app.testConfig.TestConfig;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.xml.XmlFileExportServiceImpl;
import com.epam.brest.courses.service.xml.XmlFileImportService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tools.ant.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={XmlController.class, TestConfig.class} )
@TestPropertySource({"classpath:sql-development-team.properties", "classpath:xml.properties"})

class XmlControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlControllerIT.class);

    private int id = 1;

    @Value("${pro.zip_file}")
    private String projects_zip_file;

    @Value("${dev.zip_file}")
    private String developers_zip_file;

    @Autowired
    private XmlController xmlController;

    @Autowired
    private XmlFileExportServiceImpl xmlFileExportService;

    @Autowired
    private XmlFileImportService xmlFileImportService;

    @Autowired
    private DevelopersService developersService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private MockMvcExportService exportService = new MockMvcExportService();

    private MockMvcImportService importService = new MockMvcImportService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(xmlController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(), new ByteArrayHttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void shouldExportProjectsToXml() throws Exception {

        LOGGER.debug("exportProjectsToXml()");
        List<Projects> projectsList = createListOfProjects();

        exportService.exportProjectsToXml(projectsList);
    }

    @Test
    void shouldExportDevelopersToXml() throws Exception {

        LOGGER.debug("shouldExportDevelopersToXml");

        List<Developers> developersList = createListOfDevelopers();

        exportService.exportDevelopersToXml(developersList);
    }

    @Test
    void shouldImportXmlToProjects() throws Exception {

        File file = new File(projects_zip_file);
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file"
                , file.getName()
                ,"text/plain"
                , IOUtils.toByteArray(input));
        List<Projects> projectsList = importService.parseProjectsXMLFile(multipartFile);

        assertNotNull(projectsList);
    }

    @Test
    void shouldImportXmlToDevelopers() throws Exception {

        File file = new File(developers_zip_file);
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file"
                , file.getName()
                ,"text/plain"
                , IOUtils.toByteArray(input));
        List<Developers> developersList = importService.parseDevelopersXMLFile(multipartFile);

        assertNotNull(developersList);
    }

    class MockMvcExportService {

        public void exportProjectsToXml(List<Projects> projectsList) throws Exception {

            LOGGER.debug("exportProjectsToXml({})", projectsList);

            MockHttpServletResponse response = mockMvc.perform(post("/projects/export/xml")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(projectsList))
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            LOGGER.debug("exportProjectsToXml(size of projectsList ={}) = {} OK", projectsList.size(), response.getStatus());

        }

        public void exportDevelopersToXml(List<Developers> developersList) throws Exception {

            LOGGER.debug("exportDevelopersToXml({})", developersList);

            MockHttpServletResponse response = mockMvc.perform(post( "/developers/export/xml")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(developersList))
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            LOGGER.debug("exportDevelopersToXml(size of developersList ={}) = {} OK", developersList.size(), response.getStatus());

        }
    }
        class MockMvcImportService {

            public List<Projects> parseProjectsXMLFile(MockMultipartFile multipartFile) throws Exception {

                LOGGER.debug("parseProjectsXMLFile({})", multipartFile);

                MockHttpServletResponse response = mockMvc.perform(multipart("/projects/import/xml").file(multipartFile))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();
                assertNotNull(response);

                return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Projects>>() {
                });
            }

            public List<Developers> parseDevelopersXMLFile(MockMultipartFile multipartFile) throws Exception {

                LOGGER.debug("parseDevelopersXMLFile({})", multipartFile);

                MockHttpServletResponse response = mockMvc.perform(multipart("/developers/import/xml").file(multipartFile))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();
                assertNotNull(response);

                return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Developers>>() {
                });
            }

        }

        public List<Projects> createListOfProjects(){

            List<Projects> projectsList = new ArrayList<>();
            Projects project = createProjects();
            projectsList.add(project);
            Projects project1 = createProjects();
            projectsList.add(project1);
            Projects project2 = createProjects();
            projectsList.add(project2);

            return projectsList;
        }

    public List<Developers> createListOfDevelopers(){

        List<Developers> developersList = new ArrayList<>();
        Developers developer = createDevelopers();
        developersList.add(developer);
        Developers developer1 = createDevelopers();
        developersList.add(developer1);
        Developers developer2 = createDevelopers();
        developersList.add(developer2);

        return developersList;
    }
        public Projects createProjects() {

            Projects project = new Projects();
            project.setDescription("Test");
            project.setProjectId(id);
            id++;
            return project;
        }

        public Developers createDevelopers() {

            Developers developer = new Developers();
            developer.setFirstName("Test");
            developer.setLastName("Developer");
            developer.setDeveloperId(id);
            id++;

            return developer;
        }

}