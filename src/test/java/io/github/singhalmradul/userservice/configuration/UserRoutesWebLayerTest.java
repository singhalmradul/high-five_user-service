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
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import io.github.singhalmradul.userservice.handlers.UserHandler;
import io.github.singhalmradul.userservice.services.UserService;
import io.github.singhalmradul.userservice.validators.CompleteUserValidator;
import io.github.singhalmradul.userservice.validators.UUIDValidator;
import io.github.singhalmradul.userservice.views.CompleteUser;
import io.github.singhalmradul.userservice.views.MinimalUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
@Import({ RouterConfiguration.class, UserHandler.class, UUIDValidator.class, CompleteUserValidator.class})
@DisplayName("user routes web layer test")
class UserRoutesWebLayerTest {

    @Autowired
    @Qualifier("userRoutes")
    private RouterFunction<ServerResponse> userRoutes;

    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    private CompleteUser user;

    @BeforeEach
    @SuppressWarnings("null")
    void beforeEach() {

        webTestClient = bindToRouterFunction(userRoutes).build();
        // Create a user object for testing
        user = CompleteUser.builder()
            .email("e@mail.com")
            .id(randomUUID())
            .displayName("displayName")
            .avatar("#")
            .username("username")
            .build();
    }

    @DisplayName("GET /users/{id}: get user")
    @Test
    @SuppressWarnings("null")
    void testGetUserById_shouldReturnHttpStatusCodeOkAndBodyIsUser() throws Exception {

        when(userService.getUserById(user.id(), CompleteUser.class))
        .thenReturn(Mono.just(user));

        webTestClient
            .get().uri(URI.create("/users/" + user.id())).exchange()
            .expectStatus().isOk()
            .expectBody(CompleteUser.class).consumeWith(response -> {
                CompleteUser returnedUser = response.getResponseBody();
                assertEquals(user.id(), returnedUser.id());
                assertEquals(user.email(), returnedUser.email());
                assertEquals(user.displayName(), returnedUser.displayName());
                assertEquals(user.avatar(), returnedUser.avatar());
                assertEquals(user.username(), returnedUser.username());
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
        MinimalUser minimalUser = new MinimalUser(
            user.id(),
            user.username(),
            user.avatar()
        );

        when(userService.getUserById(user.id(), MinimalUser.class))
        .thenReturn(Mono.just(minimalUser));

        webTestClient
            .get().uri(URI.create("/users/" + user.id() + "?view=minimal")).exchange()
            .expectStatus().isOk()
            .expectBody(MinimalUser.class).consumeWith(response -> {
                MinimalUser returnedUser = response.getResponseBody();
                assertEquals(minimalUser.id(), returnedUser.id());
                assertEquals(minimalUser.displayName(), returnedUser.displayName());
                assertEquals(minimalUser.avatar(), returnedUser.avatar());
            });
    }

    @DisplayName("GET /users: get all users")
    @Test
    @SuppressWarnings("null")
    void testGetAllUsers_shouldReturnHttpStatusCodeOkWithBodyListOfAllUsers() throws Exception {
        List<CompleteUser> users = new ArrayList<>();

        for (var f = 0; f < 3; f++) {
            CompleteUser tempUser = CompleteUser.builder()
                .email("e" + f + "@mail.com")
                .id(randomUUID())
                .displayName("displayName" + f)
                .avatar(user.avatar())
                .username("username" + f)
                .build();

            users.add(tempUser);
        }

        when(userService.getAllUsers(CompleteUser.class))
        .thenReturn(Flux.fromIterable(users));

        webTestClient.get().uri(URI.create("/users")).exchange()
            .expectStatus().isOk()
            .expectBodyList(CompleteUser.class).hasSize(3)
            .consumeWith(response -> {
                List<CompleteUser> returnedUsers = response.getResponseBody();
                for (var f = 0; f < 3; f++) {
                    assertEquals(users.get(f).id(), returnedUsers.get(f).id());
                    assertEquals(users.get(f).email(), returnedUsers.get(f).email());
                    assertEquals(users.get(f).displayName(), returnedUsers.get(f).displayName());
                    assertEquals(users.get(f).avatar(), returnedUsers.get(f).avatar());
                    assertEquals(users.get(f).username(), returnedUsers.get(f).username());
                }
            });
    }

    @DisplayName("GET /users: get all users, view = minimal")
    @Test
    @SuppressWarnings("null")
    void testGetAllUsers_whenQueryParamViewIsEqualToMinimal_shouldReturnHttpStatusCodeOkWihBodyListOfMinimalUsers() throws Exception {
        List<MinimalUser> users = new ArrayList<>();

        for (var f = 0; f < 3; f++) {
            MinimalUser tempUser = new MinimalUser(
                randomUUID(),
                user.username() + f,
                user.avatar() + f
            );

            users.add(tempUser);
        }

        when(userService.getAllUsers(MinimalUser.class))
        .thenReturn(Flux.fromIterable(users));

        webTestClient.get().uri(URI.create("/users?view=minimal")).exchange()
            .expectStatus().isOk()
            .expectBodyList(MinimalUser.class).hasSize(3)
            .consumeWith(response -> {
                List<MinimalUser> returnedUsers = response.getResponseBody();
                for (var f = 0; f < 3; f++) {
                    assertEquals(users.get(f).id(), returnedUsers.get(f).id());
                    assertEquals(users.get(f).displayName(), returnedUsers.get(f).displayName());
                    assertEquals(users.get(f).avatar(), returnedUsers.get(f).avatar());
                }
            });
    }

    // @DisplayName("POST /users: create user")
    // @Test
    // @SuppressWarnings("null")
    // void testCreateUser_shouldReturnHttpStatusCodeCreatedAndBodyIsUser() throws Exception {
    //     when(userService.createUser(user))
    //     .thenReturn(Mono.just(user));

    //     webTestClient
    //         .post().uri(URI.create("/users"))
    //         .bodyValue(user)
    //         .exchange()
    //         .expectStatus().isCreated()
    //         .expectBody(User.class).consumeWith(response -> {
    //             User returnedUser = response.ResponseBody();
    //             assertEquals(user.id(), returnedUser.id());
    //             assertEquals(user.email(), returnedUser.email());
    //             assertEquals(user.displayName(), returnedUser.displayName());
    //             assertEquals(user.profilePictureUrl(), returnedUser.profilePictureUrl());
    //             assertEquals(user.username(), returnedUser.username());
    //         });
    // }

    // @DisplayName("POST /users: create user, invalid user")
    // @Test
    // @SuppressWarnings({ "null", "unused" })
    // void testCreateUser_whenUserIsInvalid_shouldReturnHttpStatusBadRequest() throws Exception {
    //     Object invalidUser = new Object() {
    //         public String getusername() {
    //             return null;
    //         }
    //     };

    //     webTestClient
    //         .post().uri(URI.create("/users"))
    //         .bodyValue(invalidUser)
    //         .exchange()
    //         .expectStatus().isBadRequest();
    // }
}
