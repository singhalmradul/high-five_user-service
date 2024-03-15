package io.github.singhalmradul.userservice.configuration;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToRouterFunction;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.github.singhalmradul.userservice.handlers.UserHandler;
import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.services.UserService;
import io.github.singhalmradul.userservice.validators.UUIDValidator;
import io.github.singhalmradul.userservice.views.MinimalUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
@Import({ RouterConfiguration.class, UserHandler.class, UUIDValidator.class })
@DisplayName("web layer test")
class UserRoutesWebLayerTest {

    @Autowired
    @Qualifier("userRoutes")
    private RouterFunction<ServerResponse> userRoutes;

    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    @SuppressWarnings("null")
    void beforeEach() {

        webTestClient = bindToRouterFunction(userRoutes).build();
        // Create a user object for testing
        user = new User();
        user.setEmail("e@mail.com");
        user.setId(randomUUID());
        user.setDisplayName("displayName");
        user.setProfilePictureUrl("#");
        user.setUsername("username");
    }

    @DisplayName("valid id, minimal = false")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_whenIdIsInvalidAndMinimalIsFalse_viewFullUser() throws Exception {
        // Mock the userService to return the user object
        when(userService.getUserById(user.getId(), User.class))
        .thenReturn(Mono.just(user));

        webTestClient
            .get().uri(URI.create("/users/" + user.getId())).exchange()
            .expectStatus().isOk()
            .expectBody(User.class).consumeWith(response -> {
                User returnedUser = response.getResponseBody();
                assertEquals(user.getId(), returnedUser.getId());
                assertEquals(user.getEmail(), returnedUser.getEmail());
                assertEquals(user.getDisplayName(), returnedUser.getDisplayName());
                assertEquals(user.getProfilePictureUrl(), returnedUser.getProfilePictureUrl());
                assertEquals(user.getUsername(), returnedUser.getUsername());
            });
    }

    @DisplayName("invalid id,  minimal = false")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_whenIdIsValidAndMinimalIsFalse_viewFullUser() throws Exception {
        var id = 1;

        webTestClient
            .get().uri(URI.create("/users/" + id)).exchange()
            .expectStatus().isBadRequest();
    }

    @DisplayName("invalid id,  minimal = true")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_whenIdIsValidAndMinimalIsTrue_viewFullUser() throws Exception {
        var id = 1;

        webTestClient
            .get().uri(URI.create("/users/" + id + "?minimal=true")).exchange()
            .expectStatus().isBadRequest();
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

        // Mock the userService to return the minimalUser object
        when(userService.getUserById(user.getId(), MinimalUser.class))
        .thenReturn(Mono.just(minimalUser));

        webTestClient
            .get().uri(URI.create("/users/" + user.getId() + "?minimal=true")).exchange()
            .expectStatus().isOk()
            .expectBody(MinimalUser.class).consumeWith(response -> {
                MinimalUser returnedUser = response.getResponseBody();
                assertEquals(minimalUser.id(), returnedUser.id());
                assertEquals(minimalUser.username(), returnedUser.username());
                assertEquals(minimalUser.profilePictureUrl(), returnedUser.profilePictureUrl());
            });
    }

    @DisplayName("all users, minimal = false")
    @Test
    @SuppressWarnings("null")
    void testGetAllUsers_whenMinimalIsFalse_viewFullUsers() throws Exception {
        List<User> users = new ArrayList<>();

        // Create multiple user objects for testing
        for (var f = 0; f < 3; f++) {
            User tempUser = new User();

            tempUser.setEmail("e" + f + "@mail.com");
            tempUser.setId(randomUUID());
            tempUser.setDisplayName("displayName" + f);
            tempUser.setProfilePictureUrl(user.getProfilePictureUrl());
            tempUser.setUsername("username" + f);

            users.add(tempUser);
        }

        // Mock the userService to return the list of users
        when(userService.getAllUsers(User.class))
        .thenReturn(Flux.fromIterable(users));

        webTestClient.get().uri(URI.create("/users")).exchange()
            .expectStatus().isOk()
            .expectBodyList(User.class).hasSize(3)
            .consumeWith(response -> {
                List<User> returnedUsers = response.getResponseBody();
                for (var f = 0; f < 3; f++) {
                    assertEquals(users.get(f).getId(), returnedUsers.get(f).getId());
                    assertEquals(users.get(f).getEmail(), returnedUsers.get(f).getEmail());
                    assertEquals(users.get(f).getDisplayName(), returnedUsers.get(f).getDisplayName());
                    assertEquals(users.get(f).getProfilePictureUrl(), returnedUsers.get(f).getProfilePictureUrl());
                    assertEquals(users.get(f).getUsername(), returnedUsers.get(f).getUsername());
                }
            });
    }

    @DisplayName("all users, minimal = true")
    @Test
    @SuppressWarnings("null")
    void testGetAllUsers_whenMinimalIsTrue_viewMinimalUsers() throws Exception {
        List<MinimalUser> users = new ArrayList<>();

        // Create multiple minimalUser objects for testing
        for (var f = 0; f < 3; f++) {
            MinimalUser tempUser = new MinimalUser(
                randomUUID(),
                user.getUsername() + f,
                user.getProfilePictureUrl() + f
            );

            users.add(tempUser);
        }

        // Mock the userService to return the list of minimalUsers
        when(userService.getAllUsers(MinimalUser.class))
        .thenReturn(Flux.fromIterable(users));

        webTestClient.get().uri(URI.create("/users?minimal=true")).exchange()
            .expectStatus().isOk()
            .expectBodyList(MinimalUser.class).hasSize(3)
            .consumeWith(response -> {
                List<MinimalUser> returnedUsers = response.getResponseBody();
                for (var f = 0; f < 3; f++) {
                    assertEquals(users.get(f).id(), returnedUsers.get(f).id());
                    assertEquals(users.get(f).username(), returnedUsers.get(f).username());
                    assertEquals(users.get(f).profilePictureUrl(), returnedUsers.get(f).profilePictureUrl());
                }
            });
    }
}
