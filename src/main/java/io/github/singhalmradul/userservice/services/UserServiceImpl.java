package io.github.singhalmradul.userservice.services;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.exceptions.UserNotFoundException;
import io.github.singhalmradul.userservice.repositories.UserRepository;
import io.github.singhalmradul.userservice.views.UserView;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public <T extends UserView> Collection<T> getAllUsers(Class<T> type) {

        return userRepository.findBy(type);

    }

    @Override
    public <T extends UserView> T getUserById(UUID id, Class<T> type) {

        Optional<T> user = userRepository.findById(id, type);

        if (user.isEmpty()) {
            throw new UserNotFoundException("no user exists for the provided id" +  id);
        }

        return user.get();

    }

}
