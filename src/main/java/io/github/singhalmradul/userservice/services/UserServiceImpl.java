package io.github.singhalmradul.userservice.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.exceptions.UserNotFoundException;
import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.repositories.UserRepository;
import io.github.singhalmradul.userservice.views.UserMinimalView;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private <T> MappingJacksonValue wrapUsers(T users, boolean minimal)
    {
        @SuppressWarnings("null")
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);

        if (minimal) {

            mappingJacksonValue.setSerializationView(UserMinimalView.class);

        }

        return mappingJacksonValue;
    }

    @Override
    public MappingJacksonValue getAllUsers(boolean minimal) {

        List<User> users = userRepository.findAll();

        return wrapUsers(users, minimal);

    }

    @Override
    public MappingJacksonValue getUser(UUID id, boolean minimal) {

        @SuppressWarnings("null")
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("no user exists for the provided id");
        }
        
        return wrapUsers(user, minimal);
    }

}
