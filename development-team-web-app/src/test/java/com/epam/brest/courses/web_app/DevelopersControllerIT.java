package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Developers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
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

        Developers developer = new Developers()
                .setDeveloperId(index)
                .setLastName("LastName" + index)
                .setFirstName("FirstName" + index);
        return developer;
    }
}