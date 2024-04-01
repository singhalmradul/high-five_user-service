package io.github.singhalmradul.userservice.configuration;


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
            .GET("/users/{id}", handler::getUserById)
            // .POST("/users", handler::createUser)
            .build();
    }
}
