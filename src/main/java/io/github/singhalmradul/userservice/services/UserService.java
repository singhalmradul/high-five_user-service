package io.github.singhalmradul.userservice.services;

import java.util.UUID;

import io.github.singhalmradul.userservice.views.UserView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    <T extends UserView> Flux<T> getAllUsers(Class<T> type);

    <T extends UserView> Mono<T> getUserById(UUID id, Class<T> type);

}
