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
import org.springframework.web.server.ServerWebInputException;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.services.UserService;
import io.github.singhalmradul.userservice.validators.CompleteUserValidator;
import io.github.singhalmradul.userservice.validators.UUIDValidator;
import io.github.singhalmradul.userservice.views.CompleteUser;
import io.github.singhalmradul.userservice.views.MinimalUser;
import io.github.singhalmradul.userservice.views.UserView;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserHandler {

    @Lazy
    private UUIDValidator uuidValidator;

    @Lazy
    private CompleteUserValidator userValidator;

    private UserService userService;

    private boolean isMinimalRequest(ServerRequest request) {
        return request.queryParam("view").map("minimal"::equals).orElse(false);
    }

    private Class<? extends UserView> getViewType(ServerRequest request) {
        return isMinimalRequest(request) ? MinimalUser.class : CompleteUser.class;
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {

        return userService
            .getAllUsers(getViewType(request))
            .collectList()
            .flatMap(users -> ok().bodyValue(users));
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {

        String idStr = request.pathVariable("id");
        validateUUID(idStr);

        UUID id = UUID.fromString(idStr);
        return userService
            .getUserById(id, getViewType(request))
            .flatMap(user -> ok().bodyValue(user));

    }

    // public Mono<ServerResponse> createUser(ServerRequest request) {

    //     return request.bodyToMono(User.class)
    //         .switchIfEmpty(
    //             Mono.error(new ServerWebInputException("unrecognized value in request body"))
    //         )
    //         .doOnNext(this::validateUser)
    //         .flatMap(user -> userService.createUser(user))
    //         .flatMap(user -> created(URI.create("/users/" + user.getId())).bodyValue(user));
    // }

    private void validateUUID(String id) {

        Errors errors = new BeanPropertyBindingResult(id, "id");

        uuidValidator.validate(id, errors);

        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }

    private void validateUser(User user) {

        Errors errors = new BeanPropertyBindingResult(user, "user");

        userValidator.validate(user, errors);

        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
