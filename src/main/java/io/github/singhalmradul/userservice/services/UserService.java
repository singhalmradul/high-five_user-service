package io.github.singhalmradul.userservice.services;

import java.util.UUID;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.views.UserView;
import reactor.core.publisher.Mono;

public interface UserService {

    <T extends UserView> Mono<T> getUserById(UUID id, Class<T> type);

    Mono<User> createUser(User user);

}
