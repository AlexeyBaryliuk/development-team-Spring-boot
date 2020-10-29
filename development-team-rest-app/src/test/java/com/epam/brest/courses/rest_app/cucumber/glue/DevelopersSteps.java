package com.epam.brest.courses.rest_app.cucumber.glue;

import com.epam.brest.courses.daoJPA.DevelopersRepository;
import com.epam.brest.courses.model.Developers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
public class DevelopersSteps {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private List<Developers> expectedList;
    private List<Developers> actualList;
    private Developers developerBefore;
    private Developers developerAfter;

    private ObjectMapper objectMapper;
    private Integer developerId;
    private final String URL = "http://localhost:8088/developers";

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
    // get all developers
    @Given("^the following developers$")
    public void  givenTheFollowingDevelopers(List<Developers> developersList){

        expectedList.addAll(developersList);
        developersRepository.saveAll(expectedList);

    }

    @When("^the user request all the developers$")
    public void theUserRequestAllTheDevelopers() throws JsonProcessingException {

        actualList.addAll(Arrays.asList(
                objectMapper.readValue(
                    testRestTemplate.getForEntity(URL, String.class)
                                    .getBody(),Developers[].class)));

    }

    @Then("^all the developers are returned$")
    public void allTheDevelopersAreReturned() throws Throwable {
        assertEquals(expectedList.size(), actualList.size());

    }

    // should update developer

    @Given("^the following developer with name (.*) and lastname (.*)$")
    public void theFollowingDeveloperWithNameJohnAndLastnameConnor(final String firstname
                                                                 , final String lastName) {
        newDeveloper(firstname,lastName);

        developerId = developersRepository.saveAndFlush(developerBefore).getDeveloperId();

    }

    @When("^the developer was updated with lastname (.*)$")
    public void theDeveloperWasUpdatedWithLastnameNewConnor(final String lastName) {

        developerBefore.setLastName(lastName);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Developers> entity = new HttpEntity<>(developerBefore,headers);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        testRestTemplate.exchange(URL + "/" + developerId , HttpMethod.PUT, entity,Integer.class );

    }

    @Then("^updated developer is back$")
    public void updatedDeveloperIsBackWithLastNameNewConnor() {

        developerAfter = developersRepository.findByDeveloperId(developerId).get();

            assertEquals(developerAfter.getDeveloperId(), developerId);
            assertEquals(developerAfter.getFirstName(), developerBefore.getFirstName());
            assertEquals("NewConnor", developerBefore.getLastName());
    }

    // delete developer

    @Given("^developer with name (.*) and lastname (.*) is present$")
    public void developerWithNameJohnAndLastnameConnorIsPresent(final String firstname
                                                                      , final String lastName) {
        newDeveloper(firstname,lastName);
        developersRepository.create(developerBefore);

    }

    @When("^the developer with developerId ([0-9]) deleted$")
    public void theDeveloperWithDeveloperIdDeleted(Integer developerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Integer> entity = new HttpEntity<>(developerId,headers);
        testRestTemplate.exchange(URL +"/" + developerId, HttpMethod.DELETE,entity,Integer.class);

    }

    @Then("^the following developer with developerId ([0-9]) isn't present$")
    public void theFollowingDeveloperWithDeveloperIdIsnTPresent(Integer developerId) {

        ResponseEntity<Developers> responseEntity = testRestTemplate.getForEntity(URL + "/" + developerId, Developers.class);

        assertNull(Objects.requireNonNull(responseEntity.getBody()).getDeveloperId());
        assertNull(Objects.requireNonNull(responseEntity.getBody()).getFirstName());
        assertNull(Objects.requireNonNull(responseEntity.getBody()).getLastName());
    }

    //find developer by developerId


    @When("^the user tries to find developer by developerId ([0-9])$")
    public void theUserTriesToFindDeveloperByDeveloperId1(Integer developerId) {
        ResponseEntity<Developers> responseEntity = testRestTemplate.getForEntity
                                                    (URL + "/" + developerId, Developers.class);
    }

    @Then("^the following developer with developerId ([0-9]) is returned$")
    public void theFollowingDeveloperWithDeveloperIdIsReturned(Integer developerId) {

        ResponseEntity<Developers> responseEntity = testRestTemplate.getForEntity(URL + "/" + developerId, Developers.class);

        assertNotNull(Objects.requireNonNull(responseEntity.getBody()));

    }
    private Developers newDeveloper(String firstname
                                  , String lastName){
        developerBefore = new Developers();
        developerBefore.setFirstName(firstname);
        developerBefore.setLastName(lastName);
        return developerBefore;
    }
}
