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
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.services.UserService;
import io.github.singhalmradul.userservice.views.UserMinimalView;

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
        user.setProfilePicture("#");
        user.setUsername("username");

    }

    @Test
    @SuppressWarnings("null")
    @DisplayName("test get full user")
    void testGetUser_whenMinimalIsFalse_viewFullUser() throws Exception {

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);

        when(userService.getUser(user.getId(), false))
            .thenReturn(mappingJacksonValue);

        mockMvc.perform(get("/users/" + user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(
                json().defaultViewInclusion(false).build()
                .registerModule(new JavaTimeModule())
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .writeValueAsString(user)
            ));

    }

    @Test
    @SuppressWarnings("null")
    @DisplayName("test get minimal user")
    void testGetUser_whenMinimalIsTrue_viewMinimalUser() throws Exception {

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setSerializationView(UserMinimalView.class);

        when(userService.getUser(user.getId(), false))
            .thenReturn(mappingJacksonValue);

        mockMvc.perform(get("/users/" + user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(
                json().defaultViewInclusion(false).build()
                .writerWithView(UserMinimalView.class)
                .writeValueAsString(user)
            ));

    }

    @Test
    @SuppressWarnings("null")
    @DisplayName("test get full all users")
    void testGetAllUsers_whenMinimalIsFalse_viewFullUsers() throws Exception {

        List<User> users = new ArrayList<>();

        for (var f = 0; f < 3; f++) {
            User tempUser = new User();
            tempUser.setCreatedAt(LocalDateTime.now());
            tempUser.setEmail("e" + f + "@mail.com");
            tempUser.setFirstName(user.getFirstName());
            tempUser.setId(UUID.randomUUID());
            tempUser.setLastName(user.getLastName());
            tempUser.setProfilePicture(user.getProfilePicture());
            tempUser.setUsername("username" + f);
        }

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);

        when(userService.getUser(user.getId(), false))
            .thenReturn(mappingJacksonValue);

        mockMvc.perform(get("/users/" + user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(
                json().defaultViewInclusion(false).build()
                .registerModule(new JavaTimeModule())
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .writeValueAsString(users)
            ));

    }

    @Test
    @SuppressWarnings("null")
    @DisplayName("test get minimal all users")
    void testGetAllUsers_whenMinimalIsTrue_viewMinimalUsers() throws Exception {

        List<User> users = new ArrayList<>();

        for (var f = 0; f < 3; f++) {
            User tempUser = new User();
            tempUser.setCreatedAt(LocalDateTime.now());
            tempUser.setEmail("e" + f + "@mail.com");
            tempUser.setFirstName(user.getFirstName());
            tempUser.setId(UUID.randomUUID());
            tempUser.setLastName(user.getLastName());
            tempUser.setProfilePicture(user.getProfilePicture());
            tempUser.setUsername("username" + f);
        }

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setSerializationView(UserMinimalView.class);

        when(userService.getUser(user.getId(), false))
            .thenReturn(mappingJacksonValue);

        mockMvc.perform(get("/users/" + user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(
                json().defaultViewInclusion(false).build()
                .writerWithView(UserMinimalView.class)
                .writeValueAsString(users)
            ));

    }

}
