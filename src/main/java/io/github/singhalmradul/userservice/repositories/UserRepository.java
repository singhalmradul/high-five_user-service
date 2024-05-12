package io.github.singhalmradul.userservice.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.github.singhalmradul.userservice.model.User;

public interface UserRepository extends MongoRepository<User, UUID> {}
