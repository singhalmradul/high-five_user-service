package io.github.singhalmradul.userservice.validators;

import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Lazy
@Component
public class UUIDValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // This validator supports String class
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String uuidStr = (String) target;
        try {
            // Try to parse the string as a UUID
            UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            // If parsing fails, register an error
            errors.rejectValue(null, "invalid UUID", "'" + uuidStr + "' is not a valid UUID");
        }
    }
}
