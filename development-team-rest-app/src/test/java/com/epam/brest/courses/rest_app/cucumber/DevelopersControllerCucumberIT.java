package com.epam.brest.courses.rest_app.cucumber;

import com.epam.brest.courses.rest_app.RestApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = {RestApplication.class
                         , DevelopersControllerCucumberIT.class}
                             , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@CucumberOptions(plugin = {"pretty"},tags = "", features = "src/test/resources/features")
public class DevelopersControllerCucumberIT {

}
