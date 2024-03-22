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
import io.github.singhalmradul.userservice.projections.UserDisplayViewProjection;
import io.github.singhalmradul.userservice.services.UserService;
import io.github.singhalmradul.userservice.validators.UUIDValidator;
import io.github.singhalmradul.userservice.validators.UserValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
@Import({ RouterConfiguration.class, UserHandler.class, UUIDValidator.class, UserValidator.class})
@DisplayName("user routes web layer test")
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
        user.setId(randomUUID());
        user.setDisplayName("displayName");
        user.setProfilePictureUrl("#");
        user.setUsername("username");
    }

    @DisplayName("GET /users/{id}: get user")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_shouldReturnHttpStatusCodeOkAndBodyIsUser() throws Exception {

        when(userService.getUserById(user.getId(), User.class))
        .thenReturn(Mono.just(user));

        webTestClient
            .get().uri(URI.create("/users/" + user.getId())).exchange()
            .expectStatus().isOk()
            .expectBody(User.class).consumeWith(response -> {
                User returnedUser = response.getResponseBody();
                assertEquals(user.getId(), returnedUser.getId());
                assertEquals(user.getDisplayName(), returnedUser.getDisplayName());
                assertEquals(user.getProfilePictureUrl(), returnedUser.getProfilePictureUrl());
                assertEquals(user.getUsername(), returnedUser.getUsername());
            });
    }

    @DisplayName("GET /users/{id}: invalid uuid")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_whenIdIsNotValidUuid_shouldReturnHttpStatusBadRequest() throws Exception {
        var id = 1;

        webTestClient
            .get().uri(URI.create("/users/" + id)).exchange()
            .expectStatus().isBadRequest();
    }

    @DisplayName("GET /users/{id}: invalid uuid,  view = minimal")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_whenQueryParamViewIsEqualToMinimal_returnHttpStatusBadRequest() throws Exception {
        var id = 1;

        webTestClient
            .get().uri(URI.create("/users/" + id + "?view=minimal")).exchange()
            .expectStatus().isBadRequest();
    }

    @DisplayName("GET /users/{id}: get user by id, view = minimal")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_whenValidIdIsProvidedAndQueryParamViewIsEqualToMinimal_shouldReturnHttpStatusCodeOkAndBodyIsMinimalUser() throws Exception {
        UserDisplayViewProjection minimalUser = new UserDisplayViewProjection(
            user.getId(),
            user.getUsername(),
            user.getProfilePictureUrl()
        );

        when(userService.getUserById(user.getId(), UserDisplayViewProjection.class))
        .thenReturn(Mono.just(minimalUser));

        webTestClient
            .get().uri(URI.create("/users/" + user.getId() + "?view=minimal")).exchange()
            .expectStatus().isOk()
            .expectBody(UserDisplayViewProjection.class).consumeWith(response -> {
                UserDisplayViewProjection returnedUser = response.getResponseBody();
                assertEquals(minimalUser.id(), returnedUser.id());
                assertEquals(minimalUser.username(), returnedUser.username());
                assertEquals(minimalUser.profilePictureUrl(), returnedUser.profilePictureUrl());
            });
    }

    @DisplayName("GET /users: get all users")
    @Test
    @SuppressWarnings("null")
    void testGetAllUsers_shouldReturnHttpStatusCodeOkWithBodyListOfAllUsers() throws Exception {
        List<User> users = new ArrayList<>();

        for (var f = 0; f < 3; f++) {
            User tempUser = new User();

            tempUser.setId(randomUUID());
            tempUser.setDisplayName("displayName" + f);
            tempUser.setProfilePictureUrl(user.getProfilePictureUrl());
            tempUser.setUsername("username" + f);

            users.add(tempUser);
        }

        when(userService.getAllUsers(User.class))
        .thenReturn(Flux.fromIterable(users));

        webTestClient.get().uri(URI.create("/users")).exchange()
            .expectStatus().isOk()
            .expectBodyList(User.class).hasSize(3)
            .consumeWith(response -> {
                List<User> returnedUsers = response.getResponseBody();
                for (var f = 0; f < 3; f++) {
                    assertEquals(users.get(f).getId(), returnedUsers.get(f).getId());
                    assertEquals(users.get(f).getDisplayName(), returnedUsers.get(f).getDisplayName());
                    assertEquals(users.get(f).getProfilePictureUrl(), returnedUsers.get(f).getProfilePictureUrl());
                    assertEquals(users.get(f).getUsername(), returnedUsers.get(f).getUsername());
                }
            });
    }

    @DisplayName("GET /users: get all users, view = minimal")
    @Test
    @SuppressWarnings("null")
    void testGetAllUsers_whenQueryParamViewIsEqualToMinimal_shouldReturnHttpStatusCodeOkWihBodyListOfMinimalUsers() throws Exception {
        List<UserDisplayViewProjection> users = new ArrayList<>();

        for (var f = 0; f < 3; f++) {
            UserDisplayViewProjection tempUser = new UserDisplayViewProjection(
                randomUUID(),
                user.getUsername() + f,
                user.getProfilePictureUrl() + f
            );

            users.add(tempUser);
        }

        when(userService.getAllUsers(UserDisplayViewProjection.class))
        .thenReturn(Flux.fromIterable(users));

        webTestClient.get().uri(URI.create("/users?view=minimal")).exchange()
            .expectStatus().isOk()
            .expectBodyList(UserDisplayViewProjection.class).hasSize(3)
            .consumeWith(response -> {
                List<UserDisplayViewProjection> returnedUsers = response.getResponseBody();
                for (var f = 0; f < 3; f++) {
                    assertEquals(users.get(f).id(), returnedUsers.get(f).id());
                    assertEquals(users.get(f).username(), returnedUsers.get(f).username());
                    assertEquals(users.get(f).profilePictureUrl(), returnedUsers.get(f).profilePictureUrl());
                }
            });
    }

    @DisplayName("POST /users: create user")
    @Test
    @SuppressWarnings("null")
    void testCreateUser_shouldReturnHttpStatusCodeCreatedAndBodyIsUser() throws Exception {
        when(userService.createUser(user))
        .thenReturn(Mono.just(user));

        webTestClient
            .post().uri(URI.create("/users"))
            .bodyValue(user)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(User.class).consumeWith(response -> {
                User returnedUser = response.getResponseBody();
                assertEquals(user.getId(), returnedUser.getId());
                assertEquals(user.getDisplayName(), returnedUser.getDisplayName());
                assertEquals(user.getProfilePictureUrl(), returnedUser.getProfilePictureUrl());
                assertEquals(user.getUsername(), returnedUser.getUsername());
            });
    }

    @DisplayName("POST /users: create user, invalid user")
    @Test
    @SuppressWarnings({ "null", "unused" })
    void testCreateUser_whenUserIsInvalid_shouldReturnHttpStatusBadRequest() throws Exception {
        Object invalidUser = new Object() {
            public String getUsername() {
                return null;
            }
        };

        webTestClient
            .post().uri(URI.create("/users"))
            .bodyValue(invalidUser)
            .exchange()
            .expectStatus().isBadRequest();
    }
}
