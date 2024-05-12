package io.github.singhalmradul.userservice.services;

import java.util.List;
import java.util.UUID;

import io.github.singhalmradul.userservice.model.UserAccountDetails;

public interface UserAccountDetailsService {

    UserAccountDetails getById(UUID id);

    List<UserAccountDetails> getAll();

    boolean existsById(UUID id);
}