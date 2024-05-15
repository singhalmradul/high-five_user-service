package io.github.singhalmradul.userservice.configuration;


import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import io.github.singhalmradul.userservice.handlers.UserHandler;

@Configuration
public class RouterConfiguration {

    @Bean
    RouterFunction<ServerResponse> userRoutes(UserHandler handler) {
        return route()
            .GET("/users", handler::getAllUsers)
            .PUT("/users/{id}", handler::updateUser)
            .GET("/users/{id}", handler::getUserById)
            .GET("/users/{id}/exists", handler::existsById)
            .PUT("/users/{id}/avatar", accept(MULTIPART_FORM_DATA), handler::updateAvatar)
            .build();
    }
}
