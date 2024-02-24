package io.github.singhalmradul.userservice.controllers;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.mockito.Mockito.when;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.github.singhalmradul.userservice.exceptions.UserNotFoundException;
import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.services.UserService;
import io.github.singhalmradul.userservice.views.MinimalUser;

@WebMvcTest(UserController.class)
@DisplayName("UserController web layer test")
class UserControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void beforeEach() {

        user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setEmail("e@mail.com");
        user.setFirstName("first");
        user.setId(UUID.randomUUID());
        user.setLastName("last");
        user.setProfilePictureUrl("#");
        user.setUsername("username");

    }

    @DisplayName("valid id, minimal = false")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_whenIdIsInvalidAndMinimalIsFalse_viewFullUser() throws Exception {

        when(userService.getUserById(user.getId(), User.class))
            .thenReturn(user);

        mockMvc.perform(get("/users/" + user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(
                json().build()
                .registerModule(new JavaTimeModule())
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .writeValueAsString(user)
            ));

        }

    @DisplayName("invalid id,  minimal = false")
    @Test
    void testGetUserById_whenIdIsValidAndMinimalIsFalse_viewFullUser() throws Exception {

        UUID id = UUID.randomUUID();
        when(userService.getUserById(id, User.class))
            .thenThrow(new UserNotFoundException("no user exists for the provided id" + id));

        mockMvc.perform(get("/users/" + id))
            .andExpect(status().isBadRequest());

    }

    @DisplayName("invalid id,  minimal = true")
    @Test
    void testGetUserById_whenIdIsValidAndMinimalIsTrue_viewFullUser() throws Exception {

        UUID id = UUID.randomUUID();
        when(userService.getUserById(id, User.class))
            .thenThrow(new UserNotFoundException("no user exists for the provided id" + id));

        mockMvc.perform(get("/users/" + id + "minimal=true"))
            .andExpect(status().isBadRequest());

    }

    @DisplayName("valid id, minimal = true")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_whenValidIdIsProvidedAndMinimalIsTrue_viewMinimalUser() throws Exception {

        MinimalUser minimalUser = new MinimalUser(
            user.getId(),
            user.getUsername(),
            user.getProfilePictureUrl()
        );

        when(userService.getUserById(user.getId(), MinimalUser.class))
            .thenReturn(minimalUser);

        mockMvc.perform(get("/users/" + minimalUser.id() + "?minimal=true"))
            .andExpect(status().isOk())
            .andExpect(content().json(
                json().build()
                .writeValueAsString(minimalUser)
            ));

    }

    @DisplayName("all users, minimal = false")
    @Test
    @SuppressWarnings("null")
    void testGetAllUsers_whenMinimalIsFalse_viewFullUsers() throws Exception {

        List<User> users = new ArrayList<>();

        for (var f = 0; f < 3; f++) {
            User tempUser = new User();
            tempUser.setCreatedAt(LocalDateTime.now());
            tempUser.setEmail("e" + f + "@mail.com");
            tempUser.setFirstName(user.getFirstName());
            tempUser.setId(UUID.randomUUID());
            tempUser.setLastName(user.getLastName());
            tempUser.setProfilePictureUrl(user.getProfilePictureUrl());
            tempUser.setUsername("username" + f);
            users.add(tempUser);
        }

        when(userService.getAllUsers(User.class))
            .thenReturn(users);

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().json(
                json().build()
                .registerModule(new JavaTimeModule())
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .writeValueAsString(users)
            ));

    }

    @DisplayName("all users, minimal = true")
    @Test
    @SuppressWarnings("null")
    void testGetAllUsers_whenMinimalIsTrue_viewMinimalUsers() throws Exception {

        List<MinimalUser> users = new ArrayList<>();

        for (var f = 0; f < 3; f++) {
            MinimalUser tempUser = new MinimalUser(
                UUID.randomUUID(),
                user.getUsername() + f,
                user.getProfilePictureUrl() + f
            );
            users.add(tempUser);
        }

        when(userService.getAllUsers(MinimalUser.class))
            .thenReturn(users);

        mockMvc.perform(get("/users?minimal=true"))
            .andExpect(status().isOk())
            .andExpect(content().json(
                json().build()
                .writeValueAsString(users)
            ));

    }

}
