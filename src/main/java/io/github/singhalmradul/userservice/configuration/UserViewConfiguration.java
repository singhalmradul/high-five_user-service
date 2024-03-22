package io.github.singhalmradul.userservice.configuration;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.projections.UserDisplayViewProjection;
import io.github.singhalmradul.userservice.projections.UsernameAndIdViewProjection;
import io.github.singhalmradul.userservice.views.UserFullView;
import io.github.singhalmradul.userservice.views.UserView;
/**
 * @formatter:off
 */
@Configuration
public class UserViewConfiguration {

    @Bean
    Map<Class<? extends UserView>, List<Class<?>>> userViewMap() {
        return Map.of(
            UsernameAndIdViewProjection.class, asList(
                null,
                UsernameAndIdViewProjection.class
            ),
            UserDisplayViewProjection.class, asList(
                UserDisplayViewProjection.class,
                null
            ),
            UserFullView.class, asList(
                User.class,
                UserAccountDetails.class
            )
        );
    }
}
