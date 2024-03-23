package io.github.singhalmradul.userservice.validators;

import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Lazy
@Component
@SuppressWarnings("null")
public class UUIDValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target instanceof String uuid) {
            try {
                UUID.fromString(uuid);
            } catch (IllegalArgumentException e) {
                errors.rejectValue(null, "invalid UUID", "'" + uuid + "' is not a valid UUID");
            }

        } else {
            errors.rejectValue(null, "invalid UUID", "unrecognized value: '" + target + "'");
        }
    }
}
