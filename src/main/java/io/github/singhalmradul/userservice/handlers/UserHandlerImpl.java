package io.github.singhalmradul.userservice.handlers;

import static org.springframework.web.servlet.function.ServerResponse.ok;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.services.UserService;
import io.github.singhalmradul.userservice.validators.CompleteUserValidator;
import io.github.singhalmradul.userservice.validators.UUIDValidator;
import io.github.singhalmradul.userservice.views.CompleteUser;
import io.github.singhalmradul.userservice.views.MinimalUser;
import io.github.singhalmradul.userservice.views.UserView;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserHandlerImpl implements UserHandler {

    @Lazy
    private UUIDValidator uuidValidator;

    @Lazy
    private CompleteUserValidator userValidator;

    private UserService userService;

    private boolean isMinimalRequest(ServerRequest request) {
        return request.param("view").map("minimal"::equals).orElse(false);
    }

    private Class<? extends UserView> getViewType(ServerRequest request) {
        return isMinimalRequest(request) ? MinimalUser.class : CompleteUser.class;
    }

    @Override
    public ServerResponse getAllUsers(ServerRequest request) {

        String requestUserIdStr = request.param("userId").orElse(null);
        UUID requestUserId = null;

        if (requestUserIdStr != null) {
            validateUUID(requestUserIdStr);
            requestUserId = UUID.fromString(requestUserIdStr);
        }


        return ok().body(userService.getAllUsers(requestUserId, getViewType(request)));
    }

    @Override
    public ServerResponse getUserById(ServerRequest request) {

        String idStr = request.pathVariable("id");
        String requestUserIdStr = request.param("userId").orElse(null);
        UUID requestUserId = null;
        validateUUID(idStr);

        if (requestUserIdStr != null) {
            validateUUID(requestUserIdStr);
            requestUserId = UUID.fromString(requestUserIdStr);
        }

        UUID id = UUID.fromString(idStr);

        return ok().body(userService.getUserById(id, requestUserId, getViewType(request)));
    }

    private void validateUUID(String id) {

        Errors errors = new BeanPropertyBindingResult(id, "id");

        uuidValidator.validate(id, errors);

        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }

    @Override
    public ServerResponse existsById(ServerRequest request) {

        String idStr = request.pathVariable("id");
        validateUUID(idStr);

        UUID id = UUID.fromString(idStr);
        return ok().body(userService.existsById(id));
    }

    private void validateUser(User user) {

        Errors errors = new BeanPropertyBindingResult(user, "user");

        userValidator.validate(user, errors);

        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
