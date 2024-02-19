package io.github.singhalmradul.userservice.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.exceptions.UserNotFoundException;
import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.repositories.UserRepository;
import io.github.singhalmradul.userservice.views.UserViews;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private <T> MappingJacksonValue wrapUsers(T users, boolean minimal)
    {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);

        if (minimal) {

            // SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
            //     "id",
            //     "username",
            //     "profilePicture"
            // );
            // FilterProvider filters = new SimpleFilterProvider().addFilter("minimal", filter);

            mappingJacksonValue.setSerializationView(UserViews.Minimal.class);

        }

        return mappingJacksonValue;
    }

    public MappingJacksonValue getAllUsers(boolean minimal) {

        List<User> users = userRepository.findAll();

        return wrapUsers(users, minimal);

    }

    public MappingJacksonValue getUser(UUID id, boolean minimal) {

        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("no user exists for the provided id");
        }
        return wrapUsers(user, minimal);
    }

}
