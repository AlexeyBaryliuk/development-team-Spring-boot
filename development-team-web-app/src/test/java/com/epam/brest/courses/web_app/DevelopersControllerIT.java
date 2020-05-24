package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.web_app.testConfig.TestConfig;
import com.epam.brest.courses.web_app.config.viewConfig.ViewConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={TestConfig.class, ViewConfig.class} )
@TestPropertySource("classpath:sql-development-team.properties")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class DevelopersControllerIT {

    private final String COMMON_DEVELOPERS_URL = "/developers";

    @Autowired
    private WebApplicationContext wac;


    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
   public void shouldReturnDevelopersPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_DEVELOPERS_URL)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("developers"))
                .andExpect(model().attribute("developers", hasItem(
                        allOf(
                                hasProperty("developerId", is(1)),
                                hasProperty("firstName", is("Ivan")),
                                hasProperty("lastName", is("Ivanov"))
                        )
                )))
                .andExpect(model().attribute("developers", hasItem(
                        allOf(
                                hasProperty("developerId", is(2)),
                                hasProperty("firstName", is("Petr")),
                                hasProperty("lastName", is("Petrov"))
                                )
                )))
                ;
    }

    @Test
    public void shouldOpenEditDeveloperPageById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_DEVELOPERS_URL + "/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("developer"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("developer", hasProperty("developerId", is(1))))
                .andExpect(model().attribute("developer", hasProperty("firstName", is("Ivan"))))
                .andExpect(model().attribute("developer", hasProperty("lastName", is("Ivanov"))))
                .andExpect(model().attribute("developers", hasItem(
                        allOf(
                                hasProperty("developerId", is(1)),
                                hasProperty("firstName", is("Ivan")),
                                hasProperty("lastName", is("Ivanov"))
                        )
                )));
    }

    @Test
    public void shouldReturnToDeveloperPageIfDeveloperNotFoundById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/developers/99999")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/developers"));
    }
    @Test
    public void shouldUpdateEmployeeAfterEdit() throws Exception {

        Developers developer = create(1);

        mockMvc.perform(
                MockMvcRequestBuilders.post(COMMON_DEVELOPERS_URL + "/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("developerId", String.valueOf(developer.getDeveloperId()))
                        .param("firstName", developer.getFirstName())
                        .param("lastName", developer.getLastName())
                        .sessionAttr("developer", developer)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COMMON_DEVELOPERS_URL))
                .andExpect(redirectedUrl(COMMON_DEVELOPERS_URL));
    }

    @Test
    public void shouldOpenNewDeveloperPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_DEVELOPERS_URL + "/developer")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("developer"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("developer", isA(Developers.class)));
    }

    @Test
    public void shouldAddNewDeveloper() throws Exception {

        Developers developer = create(1);

        mockMvc.perform(
                MockMvcRequestBuilders.post(COMMON_DEVELOPERS_URL + "/developer")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", developer.getFirstName())
                        .param("lastName", developer.getLastName())
                        .sessionAttr("developer", developer)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COMMON_DEVELOPERS_URL))
                .andExpect(redirectedUrl(COMMON_DEVELOPERS_URL));
    }


    @Test
    public void shouldDeleteDeveloper() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMON_DEVELOPERS_URL + "/2/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COMMON_DEVELOPERS_URL))
                .andExpect(redirectedUrl(COMMON_DEVELOPERS_URL));
    }

    private Developers create(int index){

        Developers developer = new Developers();
                developer.setDeveloperId(index);
                developer.setLastName("LastName" + index);
                developer.setFirstName("FirstName" + index);
        return developer;
    }
}