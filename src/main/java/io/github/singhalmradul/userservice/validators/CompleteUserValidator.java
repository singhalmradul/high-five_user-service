package io.github.singhalmradul.userservice.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.github.singhalmradul.userservice.views.CompleteUser;
import jakarta.ws.rs.ext.ParamConverter.Lazy;

@Lazy
@Component
@SuppressWarnings("null")
public class CompleteUserValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return CompleteUser.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target instanceof CompleteUser user) {
            if (user.username() == null || user.username().isEmpty()) {
                errors.rejectValue("username", "username.empty", "username cannot be empty");
            }
            if (user.email() == null || user.email().isEmpty()) {
                errors.rejectValue("email", "email.empty", "email cannot be empty");
            }
        }

        else {
            errors.rejectValue(null, "invalid user", "unrecognized value: '" + target + "'");
        }
    }

}
