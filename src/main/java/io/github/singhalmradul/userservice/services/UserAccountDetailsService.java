package io.github.singhalmradul.userservice.services;

import java.util.UUID;

import io.github.singhalmradul.userservice.model.UserAccountDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserAccountDetailsService {

    Mono<UserAccountDetails> getById(UUID id);

    Flux<UserAccountDetails> getAll();
}