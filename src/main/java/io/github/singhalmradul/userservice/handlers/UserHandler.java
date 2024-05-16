package io.github.singhalmradul.userservice.handlers;

import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

public interface UserHandler {

    ServerResponse getAllUsers(ServerRequest request);

    ServerResponse getUserById(ServerRequest request);

    ServerResponse existsById(ServerRequest request);

    ServerResponse updateUser(ServerRequest request);

    ServerResponse updateAvatar(ServerRequest request);

}