package io.github.singhalmradul.userservice.controllers;

import java.util.Collection;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.services.UserService;
import io.github.singhalmradul.userservice.views.MinimalUser;
import io.github.singhalmradul.userservice.views.UserView;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Collection<? extends UserView> getAllUsers(

        @RequestParam(defaultValue = "false")
        boolean minimal

    ) {

        if (minimal) {
            return userService.getAllUsers(MinimalUser.class);
        }

        return userService.getAllUsers(User.class);

    }

    @GetMapping("/users/{id}")
    public UserView getUser(

        @PathVariable
        UUID id,

        @RequestParam(defaultValue = "false")
        boolean minimal

    ) {

        if (minimal) {
            return userService.getUserById(id, MinimalUser.class);
        }

        return userService.getUserById(id, User.class);

    }

}
