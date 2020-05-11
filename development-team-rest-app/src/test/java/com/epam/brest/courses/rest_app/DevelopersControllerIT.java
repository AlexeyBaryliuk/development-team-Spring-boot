package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Developers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-cont.xml"})
class DevelopersControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersControllerIT.class);

    public static final String DEVELOPERS_ENDPOINT = "/developers";

    @Autowired
    private DevelopersController developersController;

    ObjectMapper objectMapper = new ObjectMapper();

    MockMvcDevelopersService developersService = new MockMvcDevelopersService();

    private MockMvc mockMvc;

    @BeforeEach
    private void before(){
        mockMvc = MockMvcBuilders.standaloneSetup(developersController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }


    @Test
    public void shouldFindAllDevelopers() throws Exception {

        List<Developers> developersList = developersService.findAll();
        assertNotNull(developersList);
        assertTrue(developersList.size() > 0);
    }

    @Test
    public void shouldFindDeveloperById() throws Exception {

        // given
        Developers developer = newDeveloper();

        Integer id = developersService.create(developer);

        // when
        Optional<Developers> optionalDeveloper = developersService.findById(id);

        // then
        assertTrue(optionalDeveloper.isPresent());
        assertEquals(optionalDeveloper.get().getDeveloperId(), id);
        assertEquals(optionalDeveloper.get().getFirstName(), developer.getFirstName());
        assertEquals(optionalDeveloper.get().getLastName(), developer.getLastName());
    }

    @Test
    public void shouldCreateDeveloper() throws Exception {

        // given
        Developers developer = newDeveloper();

        //when
        Integer id = developersService.create(developer);

        //then
        Optional<Developers> optionalAfterCreate = developersService.findById(id);
        assertNotNull(id);
        assertTrue(optionalAfterCreate.isPresent());
        assertEquals(optionalAfterCreate.get().getFirstName(),developer.getFirstName());
        assertEquals(optionalAfterCreate.get().getLastName(),developer.getLastName());
    }

    @Test
    public void shouldUpdateDeveloper() throws Exception {

        // given
        Developers developer = newDeveloper();
        Integer id = developersService.create(developer);
        Optional<Developers> optional = developersService.findById(id);
        optional.get().setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
        optional.get().setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));

        //when
        Integer result = developersService.update(optional.get());

        //then
        Optional<Developers> optionalAfterUpdate = developersService.findById(id);

        assertEquals(optionalAfterUpdate.get().getDeveloperId(),id);
        assertTrue(optionalAfterUpdate.isPresent());
        assertNotEquals(developer.getFirstName(),optionalAfterUpdate.get().getFirstName());
        assertNotEquals(developer.getLastName(),optionalAfterUpdate.get().getLastName());

    }

    @Test
    public void shouldDeleteDeveloperById() throws Exception {

        // given
        Developers developer = newDeveloper();
        Integer id = developersService.create(developer);
        List<Developers> developersListBefore = developersService.findAll();

        //when
        Integer result = developersService.delete(id);
        List<Developers> developersListAfetr = developersService.findAll();

        //then
        assertTrue(result == 1);
        assertTrue(developersListBefore.size()-1 == developersListAfetr.size());
    }

    class MockMvcDevelopersService{

            public List<Developers> findAll() throws Exception {

                LOGGER.debug("findAll()");

                MockHttpServletResponse response = mockMvc.perform(get(DEVELOPERS_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                        .andReturn().getResponse();
                assertNotNull(response);

                return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Developers>>() {
                });
            }

            public Integer create(Developers developer) throws Exception {

                LOGGER.debug("create({})", developer);
                String json = objectMapper.writeValueAsString(developer);
                MockHttpServletResponse response =
                        mockMvc.perform(post(DEVELOPERS_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isOk())
                                .andReturn().getResponse();

                return objectMapper.readValue(response.getContentAsString(), Integer.class);
            }


            public Optional<Developers> findById(Integer developerId) throws Exception {

                LOGGER.debug("findById({})", developerId);
                MockHttpServletResponse response = mockMvc.perform(get(DEVELOPERS_ENDPOINT + "/" + developerId)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                        .andReturn().getResponse();
                return Optional.of(objectMapper.readValue(response.getContentAsString(), Developers.class));
            }

            private int update(Developers developer) throws Exception {

                LOGGER.debug("update({})", developer);
                MockHttpServletResponse response =
                        mockMvc.perform(put(DEVELOPERS_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(developer))
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isOk())
                                .andReturn().getResponse();
                return objectMapper.readValue(response.getContentAsString(), Integer.class);
            }

            private int delete(Integer developerId) throws Exception {

                LOGGER.debug("delete({})", developerId);
                MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.delete(new StringBuilder(DEVELOPERS_ENDPOINT).append("/").append(developerId)
                                .toString())
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                        .andReturn().getResponse();

                return objectMapper.readValue(response.getContentAsString(), Integer.class);
            }
        }

        public static Developers newDeveloper(){

            Developers developer = new Developers();
                    developer.setFirstName(RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE));
                    developer.setLastName(RandomStringUtils.randomAlphabetic(LASTNAME_SIZE));
                    developer.setDeveloperId(1);
            return developer;
        }
}

