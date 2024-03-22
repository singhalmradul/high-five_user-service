package io.github.singhalmradul.userservice.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.projections.UserProjection;
import io.github.singhalmradul.userservice.projections.UsernameAndIdViewProjection;
import io.github.singhalmradul.userservice.repositories.UserRepository;
import io.github.singhalmradul.userservice.views.UserView;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {

    @Qualifier("userViewMap")
    private final Map<Class<? extends UserView>, List<Class<?>>> userViewMap;
    private final UserRepository userRepository;
    private final UserAccountDetailsService accountDetailsService;
    @Override
    public <T extends UserView> Mono<T> getUserById(UUID id, Class<T> type) {
        UserProjection user;
        UserAccountDetails accountDetails;
        var userProjection = userViewMap.get(type).get(0);
        var accountDetailsProjection = userViewMap.get(type).get(1);

        if (userProjection != null) {
            user = userRepository.findById(id, userProjection).block();
            if (user == null) {
                accountDetailsProjection = UsernameAndIdViewProjection.class;
        }

        if (accountDetailsProjection != null) {
            accountDetails = accountDetailsService.getByUserId(id, accountDetailsProjection).block();
        }

        try {
            return Mono.just(type
                .getConstructor(User.class, UserAccountDetails.class)
                .newInstance(user, accountDetails)
            );
        } catch (
            InstantiationException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException
            | NoSuchMethodException
            | SecurityException e
        ) {
            return Mono.error(e);
        }
    }

    @Override
    public Mono<User> createUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

}
