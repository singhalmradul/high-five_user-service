package io.github.singhalmradul.userservice.views;

import java.util.UUID;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import lombok.Builder;

@Builder
public record CompleteUser(
    UUID id,
    String username,
    String email,
    String profilePictureUrl,
    String displayName,
    String bio
) implements UserView {

    public CompleteUser(UserAccountDetails accountDetails, User user) {

        this(
            accountDetails.getUserId(),
            accountDetails.getUsername(),
            accountDetails.getEmail(),
            user.getProfilePictureUrl(),
            user.getDisplayName(),
            user.getBio()
        );
    }
}
