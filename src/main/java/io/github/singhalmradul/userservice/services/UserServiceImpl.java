package io.github.singhalmradul.userservice.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.repositories.UserRepository;
import io.github.singhalmradul.userservice.views.UserView;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAccountDetailsService accountDetailsService;

    @Override
    public <T extends UserView> List<T> getAllUsers(Class<T> type) {
        return accountDetailsService
            .getAll()
            .stream()
            .map(this::saveIfNotExists)
            .map(user -> mapToView(accountDetailsService.getById(user.getId()), user, type)).toList();

    }

    @Override
    public <T extends UserView> T getUserById(UUID id, Class<T> type) {
        var accountDetails = accountDetailsService.getById(id);
        return mapToView(accountDetailsService.getById(id), saveIfNotExists(accountDetails), type);
    }


    private User saveIfNotExists(UserAccountDetails accountDetails) {
        return userRepository
            .findById(accountDetails.getUserId())
            .orElseGet(
                () -> userRepository.save(
                    User.builder()
                    .id(accountDetails.getUserId())
                    .displayName(accountDetails.getUsername())
                    .build()
                )
            );
    }

    private <T extends UserView> T mapToView(
        UserAccountDetails accountDetails,
        User user,
        Class<T> type
    ) {
        try {
            return type
                .getConstructor(UserAccountDetails.class, User.class)
                .newInstance(accountDetails, user);
        } catch (
            InstantiationException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException
            | NoSuchMethodException
            | SecurityException e
        ) {
            throw new RuntimeException(e);
        }
    }
}
