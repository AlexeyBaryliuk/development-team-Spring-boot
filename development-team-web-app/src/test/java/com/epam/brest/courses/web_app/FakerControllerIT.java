package com.epam.brest.courses.web_app;

import com.epam.brest.courses.web_app.config.viewConfig.ViewConfig;
import com.epam.brest.courses.web_app.testConfig.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={TestConfig.class, ViewConfig.class} )
@TestPropertySource("classpath:sql-development-team.properties")
class FakerControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakerControllerIT.class);

    public static final String FAKE_ENDPOINT = "/changeTestData";

    @Autowired
    private WebApplicationContext wac;


    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void gotoChangeDataPage() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get(FAKE_ENDPOINT)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("fakeData"))
                .andExpect(model().attributeExists("locationList"));
    }


    @Test
    void changeDevelopersTestData() throws Exception {

        //given
        String locale = "en";
        Integer number = 2;
        mockMvc.perform(
                MockMvcRequestBuilders.get(FAKE_ENDPOINT + "/developers")
                        .param("locale", "en")
                        .param("number", number.toString())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("redirect:" + FAKE_ENDPOINT))
                .andExpect(redirectedUrl(FAKE_ENDPOINT));
    }

    @Test
    void changeProjectsTestData() throws Exception {

        //given
        String locale = "en";
        Integer number = 2;

        mockMvc.perform(
                MockMvcRequestBuilders.get(FAKE_ENDPOINT + "/projects")
                        .param("locale", "en")
                        .param("number", number.toString())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("redirect:" + FAKE_ENDPOINT))
                .andExpect(redirectedUrl(FAKE_ENDPOINT));

    }
}