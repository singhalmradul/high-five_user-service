package io.github.singhalmradul.userservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

import io.github.singhalmradul.userservice.model.UserAccountDetails;

public interface UserAccountDetailsService {

    @NonNull
    UserAccountDetails getById(UUID id);

    List<UserAccountDetails> getAll();

    boolean existsById(UUID id);

    @NonNull
    UserAccountDetails getByUsername(String username);

    UserAccountDetails save(UserAccountDetails accountDetails);
}