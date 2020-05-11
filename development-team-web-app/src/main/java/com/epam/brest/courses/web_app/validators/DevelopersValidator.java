package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Developers;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;

@Component
public class DevelopersValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Developers.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "firstName", "developerFirstName.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "developerLastName.empty");
        Developers developer = (Developers) target;

        if (StringUtils.hasLength(developer.getLastName())
                && developer.getLastName().length() > LASTNAME_SIZE ) {
            errors.rejectValue("lastName", "developerLastName.maxSize");
        }
        if (StringUtils.hasLength(developer.getFirstName())
                && developer.getFirstName().length() > FIRSTNAME_SIZE ) {
            errors.rejectValue("firstName", "developerFirstName.maxSize");
        }
    }
}
