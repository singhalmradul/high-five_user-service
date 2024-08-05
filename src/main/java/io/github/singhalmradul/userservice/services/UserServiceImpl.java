package io.github.singhalmradul.userservice.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.mongodb.lang.NonNull;

import io.github.singhalmradul.userservice.events.UserAccountCreationEvent;
import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.proxies.FollowServiceProxy;
import io.github.singhalmradul.userservice.repositories.UserRepository;
import io.github.singhalmradul.userservice.utilities.CloudinaryUtilities;
import io.github.singhalmradul.userservice.views.CompleteUser;
import io.github.singhalmradul.userservice.views.MinimalUser;
import io.github.singhalmradul.userservice.views.UserView;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAccountDetailsService accountDetailsService;
    private final FollowServiceProxy followServiceProxy;
    private final CloudinaryUtilities cloudinary;


    @EventListener
    public void handleUserAccountCreationEvent(UserAccountCreationEvent event) {
        var accountDetails = event.getPayload();

        log.info("Inserting : {}", accountDetails);

        userRepository.save(User
            .builder()
            .id(accountDetails.getUserId())
            .displayName(accountDetails.getUsername())
            .build()
        );
    }

    @Override
    public <T extends UserView> List<T> getAllUsers(UUID requestUserId, Class<T> type) {
        return accountDetailsService
            .getAll()
            .stream()
            .map(accountDetails ->
                mapToView(
                    accountDetails,
                    getUserById(accountDetails.getUserId()),
                    requestUserId,
                    type
                )
            ).toList();

    }

    @Override
    public <T extends UserView> T getUserById(
        UUID id,
        UUID requestUserId,
        Class<T> type
    ) {
        return mapToView(
            accountDetailsService.getById(id),
            getUserById(id),
            requestUserId,
            type
        );
    }

    private User getUserById(@NonNull UUID id) {
        return userRepository
            .findById(id)
            .orElseThrow();
    }

    private <T extends UserView> T mapToView(
        UserAccountDetails accountDetails,
        User user,
        UUID requestUserId,
        Class<T> type
    ) {
        try {
            if (type.isAssignableFrom(MinimalUser.class)) {
                return type
                    .getConstructor(UserAccountDetails.class, User.class)
                    .newInstance(accountDetails, user);
            }
            return type
                .getConstructor(UserAccountDetails.class, User.class, boolean.class)
                .newInstance(
                    accountDetails,
                    user,
                    // in case of null requestUserId
                    // or if requestUserId is same as user's id
                    // no need to invoke followServiceProxy
                    requestUserId != null
                        && !user.getId().equals(requestUserId)
                        && followServiceProxy.isUserFollowing(requestUserId, user.getId())
                );
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

    @Override
    @Transactional
    public UserView updateUser(UUID id, CompleteUser completeUser) {

        var accountDetails = accountDetailsService.getById(completeUser.id());
        var user = userRepository.findById(completeUser.id()).orElseThrow();

        accountDetails.setEmail(completeUser.email());
        accountDetails.setUsername(completeUser.username());

        user.setDisplayName(completeUser.displayName());
        user.setBio(completeUser.bio());

        return mapToView(
            accountDetailsService.save(accountDetails),
            userRepository.save(user),
            null,
            CompleteUser.class
        );
    }


    @Override
    @Transactional
    public String updateAvatar(UUID id, Part part) {

        var user = userRepository.findById(id).orElseThrow();

        user.setAvatar(cloudinary.uploadWithId(part, id.toString()));

        return userRepository.save(user).getAvatar();
    }

    @Override
    public <T extends UserView> T findUserByUsername(
        String username,
        UUID requestUserId,
        Class<T> type
    ) {

        var accountDetails = accountDetailsService.getByUsername(username);

        return mapToView(
            accountDetails,
            getUserById(accountDetails.getUserId()),
            requestUserId,
            type
        );
    }
}
