package io.github.singhalmradul.userservice.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.projections.UserProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, UUID> {

    <T extends UserProjection> Flux<T> findBy(Class<T> type);

    <T extends UserProjection> Mono<T> findById(UUID id, Class<?> userProjection);
}
