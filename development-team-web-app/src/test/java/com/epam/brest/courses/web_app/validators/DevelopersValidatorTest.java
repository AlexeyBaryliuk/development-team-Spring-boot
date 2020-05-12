package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Developers;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class DevelopersValidatorTest {

    private Developers developer;

    private DevelopersValidator developersValidator = new DevelopersValidator();

    private BindingResult result;

    @BeforeEach
    void setup(){
        developer = Mockito.mock(Developers.class);
        result = new BeanPropertyBindingResult(developer, "developers");
    }

    @Test
    void shouldRejectNullLastName(){

        //given
        Mockito.when(developer.getLastName()).thenReturn(null);

        //when
        developersValidator.validate(developer,result);

        //then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullFirstName(){

        //given
        Mockito.when(developer.getFirstName()).thenReturn(null);

        //when
        developersValidator.validate(developer,result);

        //then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyLastName(){

        //given
        Mockito.when(developer.getLastName()).thenReturn("");

        //when
        developersValidator.validate(developer,result);

        //then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyFirstName(){

        //given
        Mockito.when(developer.getFirstName()).thenReturn("");

        //when
        developersValidator.validate(developer,result);

        //then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectLastName(){

        //given
        String filled = StringUtils.repeat("*", LASTNAME_SIZE + 1);

        Mockito.when(developer.getLastName()).thenReturn(filled);

        //when
        developersValidator.validate(developer,result);

        //then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectFirstName(){

        //given
        String filled = StringUtils.repeat("*", FIRSTNAME_SIZE + 1);
        Mockito.when(developer.getFirstName()).thenReturn(filled);

        //when
        developersValidator.validate(developer,result);

        //then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldGetCorrectLastNameAndFirstName(){

        //given
        String lastName = StringUtils.repeat("*", LASTNAME_SIZE);
        String firstName = StringUtils.repeat("*", FIRSTNAME_SIZE);
        Mockito.when(developer.getLastName()).thenReturn(lastName);
        Mockito.when(developer.getFirstName()).thenReturn(firstName);
        //when
        developersValidator.validate(developer,result);

        //then
        assertFalse(result.hasErrors());
    }
}