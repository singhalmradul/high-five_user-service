package io.github.singhalmradul.userservice.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.services.UserService;
import io.github.singhalmradul.userservice.validators.UUIDValidator;
import io.github.singhalmradul.userservice.views.MinimalUser;
import io.github.singhalmradul.userservice.views.UserView;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserHandler {

    @Lazy
    private UUIDValidator uuidValidator;

    private UserService userService;

    private boolean isMinimalRequest(ServerRequest request) {
        return request.queryParam("view").map("minimal"::equals).orElse(false);
    }

    private Class<? extends UserView> getViewType(ServerRequest request) {
        return isMinimalRequest(request) ? MinimalUser.class : User.class;
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {

        return userService
            .getAllUsers(getViewType(request))
            .collectList()
            .flatMap(users -> ok().bodyValue(users));
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {

        String idStr = request.pathVariable("id");
        Errors errors = new BeanPropertyBindingResult(idStr, "id");

        uuidValidator.validate(idStr, errors);

        if (errors.hasErrors()) {
            return ServerResponse.badRequest().bodyValue(errors.getAllErrors());
        }

        UUID id = UUID.fromString(idStr);
        return userService
            .getUserById(id, getViewType(request))
            .flatMap(user -> ok().bodyValue(user));

    }
}
