package io.github.singhalmradul.userservice.services;

import java.util.List;
import java.util.UUID;

import io.github.singhalmradul.userservice.views.UserView;

public interface UserService {

    <T extends UserView> List<T> getAllUsers(Class<T> type);

    <T extends UserView> T getUserById(UUID id, Class<T> type);

    boolean existsById(UUID id);
}
