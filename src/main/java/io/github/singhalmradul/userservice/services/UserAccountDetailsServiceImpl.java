package io.github.singhalmradul.userservice.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.exceptions.UserNotFoundException;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.repositories.UserAccountDetailsRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserAccountDetailsServiceImpl implements UserAccountDetailsService {

    private final UserAccountDetailsRepository userAccountDetailsRepository;

    @Override
    public Mono<UserAccountDetails> getById(UUID id) {
        return userAccountDetailsRepository
            .findById(id)
            .map(Mono::just)
            .orElseThrow(() -> new UserNotFoundException("user with id '" + id + "' doesn't exist"));
    }

    @Override
    public Flux<UserAccountDetails> getAll() {
        return Flux.fromIterable(userAccountDetailsRepository.findAll());
    }
}
