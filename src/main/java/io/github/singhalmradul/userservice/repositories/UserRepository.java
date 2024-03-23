package io.github.singhalmradul.userservice.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.github.singhalmradul.userservice.model.User;

public interface UserRepository extends ReactiveMongoRepository<User, UUID> {}
