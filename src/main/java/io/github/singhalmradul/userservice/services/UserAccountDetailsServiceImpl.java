package io.github.singhalmradul.userservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.exceptions.UserNotFoundException;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.repositories.UserAccountDetailsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserAccountDetailsServiceImpl implements UserAccountDetailsService {

    private final UserAccountDetailsRepository userAccountDetailsRepository;

    @Override
    @NonNull
    public UserAccountDetails getById(UUID id) {
        return userAccountDetailsRepository
            .findById(id)
            .orElseThrow(() ->
                new UserNotFoundException("user with id '" + id + "' doesn't exist")
            );
    }

    @Override
    public List<UserAccountDetails> getAll() {
        return userAccountDetailsRepository.findAll();
    }

    @Override
    public boolean existsById(UUID id) {
        return userAccountDetailsRepository.existsById(id);
    }

    @Override
    @NonNull
    public UserAccountDetails getByUsername(String username) {
        return userAccountDetailsRepository
            .findByUsername(username)
            .orElseThrow(() ->
                new UserNotFoundException(
                    "user with username '" + username + "' doesn't exist"
                )
            );
    }

    @Override
    public UserAccountDetails save(UserAccountDetails accountDetails) {
        return userAccountDetailsRepository.save(accountDetails);
    }

}
