package com.epam.brest.courses.rest_app;


import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.rest_app.controllers.ExcelController;
import com.epam.brest.courses.rest_app.testConfig.TestConfig;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={ExcelController.class, TestConfig.class} )
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class ExcelControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelControllerIT.class);

    public static final String PROJECTS_EXCEL_ENDPOINT = "";

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private DevelopersService developersService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private MockMvcExportService exportService = new MockMvcExportService();

    private MockMvcImportService importService = new MockMvcImportService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(excelController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(),new ByteArrayHttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void downloadExcelProjects() throws Exception {

       List<Projects> allProjects =  projectsService.findAll();

        ByteArrayInputStream byteArrayInputStream = exportService.exportProjectsToExcel(allProjects);

        assertTrue(byteArrayInputStream.read() > 0);
    }

    @Test
    void downloadExcelDevelopers() throws Exception {

        List<Developers> allDevelopers =  developersService.findAll();

        ByteArrayInputStream byteArrayInputStream = exportService.exportDevelopersToExcel(allDevelopers);

        assertTrue(byteArrayInputStream.read() > 0);
    }

    @Test
    void importExcelProjects() throws Exception {

        File file = new File("excel/projects.xlsx");

        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file"
                , file.getName()
                , "text/plain"
                , IOUtils.toByteArray(input));

    boolean isFlag = importService.saveProjectsDataFromUploadFile(multipartFile);
    assertTrue(isFlag);
    }

    @Test
    void importExcelDevelopers() throws Exception {

        File file = new File("excel/developers.xlsx");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file"
                , file.getName()
                ,"text/plain"
                , IOUtils.toByteArray(input));

    boolean isFlag = importService.saveDevelopersDataFromUploadFile(multipartFile);
    assertTrue(isFlag);
    }

    class MockMvcExportService {

        public ByteArrayInputStream exportProjectsToExcel(List<Projects> projectsList) throws Exception {

            LOGGER.debug("exportProjectsToExcel()");

            MockHttpServletResponse response = mockMvc.perform(post(PROJECTS_EXCEL_ENDPOINT + "/projectsDownload")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(projectsList))
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            LOGGER.debug("exportProjectsToExcel(size of projectsList ={}) = {} bytes",projectsList.size(), response.getContentAsByteArray().length);

            return new ByteArrayInputStream(response.getContentAsByteArray());
        }

        public ByteArrayInputStream exportDevelopersToExcel(List<Developers> developersList) throws Exception {

            LOGGER.debug("exportProjectsToExcel()");

            MockHttpServletResponse response = mockMvc.perform(post(PROJECTS_EXCEL_ENDPOINT + "/developersDownload")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(developersList))
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            LOGGER.debug("exportProjectsToExcel(size of developersList = {}) = {} bytes",developersList.size(), response.getContentAsByteArray().length);

            return new ByteArrayInputStream(response.getContentAsByteArray());
        }
    }

    class MockMvcImportService {

        public Boolean saveDevelopersDataFromUploadFile(MockMultipartFile multipartFile) throws Exception {

            LOGGER.debug("saveDevelopersDataFromUploadFile()");

            ResultActions response = mockMvc.perform(multipart("/developersImport").file(multipartFile))
                    .andExpect(status().isOk());
            assertNotNull(response);

            return Boolean.valueOf(response.andReturn().getResponse().getContentAsString());
        }

        public Boolean saveProjectsDataFromUploadFile(MockMultipartFile multipartFile) throws Exception {

            LOGGER.debug("saveProjectsDataFromUploadFile()");

            ResultActions response = mockMvc.perform(multipart("/projectsImport").file(multipartFile))
      .andExpect(status().isOk());
            assertNotNull(response);

            return Boolean.valueOf(response.andReturn().getResponse().getContentAsString());
        }

    }
}