package com.epam.brest.courses.rest_app.cucumber.glue;

import com.epam.brest.courses.daoJPA.DevelopersRepository;
import com.epam.brest.courses.model.Developers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevelopersSteps {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private List<Developers> expectedList;
    private List<Developers> actualList;

    private ObjectMapper objectMapper;

    @Autowired
    private DevelopersRepository developersRepository;

    @Before
    public void setup(){

//        testRestTemplate = new TestRestTemplate();
        objectMapper = new ObjectMapper();
        expectedList = new ArrayList<>();
        actualList = new ArrayList<>();
        developersRepository.deleteAll();
    }
    @Given("^the following developers$")
    public void  givenTheFollowingDevelopers(List<Developers> developersList){

        expectedList.addAll(developersList);
        developersRepository.saveAll(expectedList);

    }

    @When("^the user request all the developers$")
    public void theUserRequestAllTheDevelopers() throws JsonProcessingException {

        actualList.addAll(Arrays.asList(
                objectMapper.readValue(
                    testRestTemplate.getForEntity("http://localhost:8088/developers", String.class)
                                    .getBody(),Developers[].class)));

    }

    @Then("^all the developers are returned$")
    public void allTheDevelopersAreReturned() throws Throwable {
        assertEquals(expectedList.size(), actualList.size());

    }
}
