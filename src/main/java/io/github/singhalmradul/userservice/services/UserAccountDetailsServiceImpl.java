package io.github.singhalmradul.userservice.services;

import static reactor.core.publisher.Mono.just;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.singhalmradul.userservice.exceptions.UserNotFoundException;
import io.github.singhalmradul.userservice.projections.UserAccountDetailsProjection;
import io.github.singhalmradul.userservice.repositories.UserAccountDetailsRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserAccountDetailsServiceImpl implements UserAccountDetailsService{

    private final UserAccountDetailsRepository accountDetailsRepository;

    @Override
    public <T extends UserAccountDetailsProjection> Mono<T> getByUsername(
        String username,
        Class<T> type
    ) {

        var accountDetails = accountDetailsRepository.findByUsername(username , type);
        if (accountDetails == null) {
            throw new UserNotFoundException(username + " not found");
        }

        return just(accountDetails);
    }

    @Override
    public <T extends UserAccountDetailsProjection> Mono<T> getByUserId(UUID userId, Class<T> type) {

        var accountDetails = accountDetailsRepository.findById(userId, type);
        if (accountDetails == null) {
            throw new UserNotFoundException("user with id '" + userId + "' doesn't exist");
        }

        return just(accountDetails);
    }

    @Override
    public Mono<Boolean> existsByUserId(UUID userId) {
        return just(accountDetailsRepository.existsById(userId));
    }
}
