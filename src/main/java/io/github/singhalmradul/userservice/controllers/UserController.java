package io.github.singhalmradul.userservice.controllers;

import java.util.UUID;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.singhalmradul.userservice.services.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public MappingJacksonValue getAllUsers(

        @RequestParam(defaultValue = "false")
        boolean minimal

    ) {

        return userService.getAllUsers(minimal);

    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue getUser(

        @PathVariable
        UUID id,

        @RequestParam(defaultValue = "false")
        boolean minimal

    ) {

        return userService.getUser(id, minimal);

    }

}
