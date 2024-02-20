package io.github.singhalmradul.userservice.services;

import java.util.UUID;

import org.springframework.http.converter.json.MappingJacksonValue;

public interface UserService {

    MappingJacksonValue getAllUsers(boolean minimal);

    MappingJacksonValue getUser(UUID id, boolean minimal);
    
}
