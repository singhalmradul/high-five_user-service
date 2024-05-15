package io.github.singhalmradul.userservice.services;

import java.util.List;
import java.util.UUID;

import io.github.singhalmradul.userservice.views.CompleteUser;
import io.github.singhalmradul.userservice.views.UserView;
import jakarta.servlet.http.Part;

public interface UserService {

    <T extends UserView> List<T> getAllUsers(UUID requestUserId, Class<T> type);

    <T extends UserView> T getUserById(UUID id, UUID requestUserId, Class<T> type);

    boolean existsById(UUID id);

    UserView updateUser(UUID id, CompleteUser user);

    String updateAvatar(UUID id, Part avatar);

    <T extends UserView> T findUserByUsername(String username, UUID requestUserId, Class<T> type);
}
