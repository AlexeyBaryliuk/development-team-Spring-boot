package com.epam.brest.courses.rest_app;


import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.rest_app.controllers.DownloadExcelProjectsController;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={DownloadExcelProjectsController.class, TestConfig.class} )
@TestPropertySource("classpath:sql-development-team.properties")
@ActiveProfiles("h2")
class DownloadExcelProjectsControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadExcelProjectsControllerIT.class);

    public static final String PROJECTS_EXCEL_ENDPOINT = "";

    @Autowired
    private DownloadExcelProjectsController excelProjectsController;

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
        mockMvc = MockMvcBuilders.standaloneSetup(excelProjectsController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(),new ByteArrayHttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void downloadExcelProjects() throws Exception {

       List<Projects> allProjects =  projectsService.findAll();
       LOGGER.debug("All+++++++++++++++++++ = {}", allProjects);
        ByteArrayInputStream byteArrayInputStream = exportService.exportProjectsToExcel(allProjects);
        LOGGER.debug("Byte+++++++++++++++++++ = {}", byteArrayInputStream.available());
        assertTrue(byteArrayInputStream.read() > 0);
    }

    @Test
    void downloadExcelDevelopers() throws Exception {

        List<Developers> allDevelopers =  developersService.findAll();
        LOGGER.debug("All+++++++++++++++++++ = {}", allDevelopers);
        ByteArrayInputStream byteArrayInputStream = exportService.exportDevelopersToExcel(allDevelopers);
        LOGGER.debug("Byte+++++++++++++++++++ = {}", byteArrayInputStream.available());
        assertTrue(byteArrayInputStream.read() > 0);
    }

//    @Test
//    void importExcelProjects() throws Exception {
//
////        Projects project = new Projects();
//
//        File file = new File("/home/alexey/Загрузки/projects.xlsx");
//
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file"
//                , file.getName()
//                , "text/plain"
//                , IOUtils.toByteArray(input));
//
////        project.setMultipartFile(multipartFile);
//
//    boolean isFlag = importService.saveProjectsDataFromUploadFile(multipartFile);
//    assertTrue(isFlag);
//    }

    @Test
    void importExcelDevelopers() throws Exception {

        Developers developer = new Developers();

        File file = new File("/home/alexey/Загрузки/developers.xlsx");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file"
                , file.getName()
                ,"text/plain"
                , IOUtils.toByteArray(input));

        developer.setMultipartFile(multipartFile);

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

        public Boolean saveDevelopersDataFromUploadFile(MultipartFile multipartFile) throws Exception {

            LOGGER.debug("saveDevelopersDataFromUploadFile()");

            MockHttpServletResponse response = mockMvc.perform(get(PROJECTS_EXCEL_ENDPOINT + "/developersImport")
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(String.valueOf(multipartFile))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            LOGGER.debug("saveDevelopersDataFromUploadFile(file ={}) = {} Boolean",multipartFile.getSize(), response.getContentAsString());

            return Boolean.valueOf(response.getContentAsString());
        }

        public Boolean saveProjectsDataFromUploadFile(MultipartFile multipartFile) throws Exception {

            LOGGER.debug("saveProjectsDataFromUploadFile()");

            MockHttpServletResponse response = mockMvc.perform(get(PROJECTS_EXCEL_ENDPOINT + "/projectsImport")
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(String.valueOf(multipartFile))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            LOGGER.debug("saveProjectsDataFromUploadFile(file ={}) = {} Boolean",multipartFile.getSize(), response.getContentAsString());

            return Boolean.valueOf(response.getContentAsString());
        }

    }
}