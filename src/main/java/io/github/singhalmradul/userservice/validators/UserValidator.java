package io.github.singhalmradul.userservice.validators;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.github.singhalmradul.userservice.model.User;
import jakarta.ws.rs.ext.ParamConverter.Lazy;

@Lazy
@Component
public class UserValidator implements Validator {

    Pattern usernamePattern = Pattern.compile("^[a-z][a-z0-9]{0,14}$");

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target instanceof User user) {
            if (user.getUsername() != null && !usernamePattern.matcher(user.getUsername()).matches()) {
                errors.rejectValue(
                    "username",
                    "invalid username",
                    """
                        username must be 1-15 characters long,
                        starting with a letter and containing only lowercase letters and numbers
                    """
                );
            }
        }
        else {
            errors.rejectValue(null, "invalid user", "unrecognized value: '" + target + "'");
        }
    }

}
