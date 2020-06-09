package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.rest_app.controllers.FakeController;
import com.epam.brest.courses.rest_app.testConfig.TestConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes={FakeController.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:sql-development-team.properties")
class FakeControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeControllerIT.class);

    public static final String FAKE_ENDPOINT = "/changeTestData";

    @Autowired
    private DevelopersDao developersDao;

    @Autowired
    private ProjectsDao projectsDao;

    @Autowired
    private FakeController fakeController;

    @Autowired
    ObjectMapper objectMapper;

    MockMvcFakeService fakeService = new MockMvcFakeService();

    private MockMvc mockMvc;

    @BeforeEach
    private void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(fakeController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void shouldChangeProjectsTestData() throws Exception {
        Integer numOfChanges = 2;
        Integer countBeforeChanges = projectsDao.countOfRow();

        fakeService.changeProjectsTestData("ru", numOfChanges);

        Integer countAfterChanges = projectsDao.countOfRow();

        assertEquals(countAfterChanges, countBeforeChanges * numOfChanges);
    }

    @Test
    void shouldChangeProjectsTestDataWithoutParameters() throws Exception {

        Integer countBeforeChanges = projectsDao.countOfRow();

        fakeService.changeProjectsTestDataWithoutParameters();

        Integer countAfterChanges = projectsDao.countOfRow();

        assertEquals(countAfterChanges, countBeforeChanges);
    }

    @Test
    void shouldChangeDevelopersTestData() throws Exception {

        Integer numOfChanges = 2;
        Integer countBeforeChanges = developersDao.countOfRow();
        fakeService.changeDevelopersTestData("ru", numOfChanges);
        Integer countAfterChanges = developersDao.countOfRow();

        assertEquals(countAfterChanges, countBeforeChanges*numOfChanges);
    }

    @Test
    void shouldChangeDevelopersTestDataWithoutParameters() throws Exception {

        Integer countBeforeChanges = developersDao.countOfRow();
        fakeService.changeDevelopersTestDataWithoutParameters();
        Integer countAfterChanges = developersDao.countOfRow();

        assertEquals(countAfterChanges, countBeforeChanges);
    }

    @Test
    public void shouldFindAllLocaleForFaker() throws Exception {

        List<String> localeList = fakeService.findAllLocaleForFaker();
        assertNotNull(localeList);
        assertTrue(localeList.size() > 0);
    }


    class MockMvcFakeService {

        public void changeProjectsTestData(String locale, Integer numOfChanges) throws Exception {

            LOGGER.debug("changeProjectsTestData({}, {})", locale, numOfChanges);

            MockHttpServletResponse response = mockMvc.perform(get(FAKE_ENDPOINT
                    + "/projects?locale=" + locale
                    + "&number=" + numOfChanges)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

        }

        public void changeDevelopersTestData(String locale, Integer numOfChanges) throws Exception {

            LOGGER.debug("changeDevelopersTestData({}, {})", locale, numOfChanges);

            MockHttpServletResponse response = mockMvc.perform(get(FAKE_ENDPOINT
                    + "/developers?locale=" + locale
                    + "&number=" + numOfChanges)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

        }

        public void changeDevelopersTestDataWithoutParameters() throws Exception {

            LOGGER.debug("changeDevelopersTestDataWithoutParameters()");

            MockHttpServletResponse response = mockMvc.perform(get(FAKE_ENDPOINT + "/developers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

        }

        public void changeProjectsTestDataWithoutParameters() throws Exception {

            LOGGER.debug("changeProjectsTestData()");

            MockHttpServletResponse response = mockMvc.perform(get(FAKE_ENDPOINT + "/projects")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

        }

        public List<String> findAllLocaleForFaker() throws Exception {

            MockHttpServletResponse response = mockMvc.perform(get(FAKE_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();

            assertNotNull(response);
        return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<String>>() {
        });
        }
    }
}