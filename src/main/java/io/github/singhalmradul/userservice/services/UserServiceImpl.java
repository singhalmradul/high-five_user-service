package io.github.singhalmradul.userservice.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.exceptions.UserNotFoundException;
import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.repositories.UserRepository;
import io.github.singhalmradul.userservice.views.UserView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public <T extends UserView> Flux<T> getAllUsers(Class<T> type) {

        return userRepository.findBy(type);

    }

    @Override
    public <T extends UserView> Mono<T> getUserById(UUID id, Class<T> type) {

        var user = userRepository.findById(id, type);
        return user.doOnNext(user1 -> {
            if (user1 == null) {
                throw new UserNotFoundException("user with id '" + id + "' doesn't exist");
            }
        });
    }

    @Override
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

}
