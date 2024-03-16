package io.github.singhalmradul.userservice.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.github.singhalmradul.userservice.model.User;
import jakarta.ws.rs.ext.ParamConverter.Lazy;

@Lazy
@Component
public class UserValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target instanceof User user) {
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                errors.rejectValue("username", "username.empty", "username cannot be empty");
            }
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                errors.rejectValue("email", "email.empty", "email cannot be empty");
            }
        }

        else {
            errors.rejectValue(null, "invalid user", "unrecognized value: '" + target + "'");
        }
    }

}
