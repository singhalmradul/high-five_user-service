package io.github.singhalmradul.userservice.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.proxies.FollowServiceProxy;
import io.github.singhalmradul.userservice.repositories.UserRepository;
import io.github.singhalmradul.userservice.views.CompleteUser;
import io.github.singhalmradul.userservice.views.UserView;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor_ = @Autowired)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAccountDetailsService accountDetailsService;
    private final FollowServiceProxy followServiceProxy;

    @Override
    public <T extends UserView> List<T> getAllUsers(Class<T> type) {
        List<T> userList = accountDetailsService
            .getAll()
            .stream()
            .map(this::saveIfNotExists)
            .map(user -> mapToView(accountDetailsService.getById(user.getId()), user, type)).toList();

        if (CompleteUser.class.equals(type)) {
            userList.forEach(user -> {
                var followers = followServiceProxy.getFollowers(user.id());
                var following = followServiceProxy.getFollowing(user.id());
                ((CompleteUser) user).followers().addAll(followers);
                ((CompleteUser) user).following().addAll(following);
            });
        }
        return userList;

    }

    @Override
    public <T extends UserView> T getUserById(UUID id, Class<T> type) {
        var accountDetails = accountDetailsService.getById(id);
        T user = mapToView(accountDetailsService.getById(id), saveIfNotExists(accountDetails), type);
        if (CompleteUser.class.equals(type)) {
            var followers = followServiceProxy.getFollowers(user.id());
            var following = followServiceProxy.getFollowing(user.id());
            ((CompleteUser) user).followers().addAll(followers);
            ((CompleteUser) user).following().addAll(following);
        }

        return user;
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

    @Override
    public boolean existsById(UUID id) {

        return userRepository.existsById(id) || accountDetailsService.existsById(id);
    }
}
