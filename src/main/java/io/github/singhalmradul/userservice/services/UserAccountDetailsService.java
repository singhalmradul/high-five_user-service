package io.github.singhalmradul.userservice.services;

import java.util.UUID;

import io.github.singhalmradul.userservice.projections.UserAccountDetailsProjection;
import reactor.core.publisher.Mono;

public interface UserAccountDetailsService {

    <T extends UserAccountDetailsProjection> Mono<T> getByUsername(String username, Class<T> type);

    <T extends UserAccountDetailsProjection> Mono<T> getByUserId(UUID userId, Class<?> accountDetailsProjection);

    Mono<Boolean> existsByUserId(UUID userId);
}
