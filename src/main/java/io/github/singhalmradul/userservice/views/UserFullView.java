package io.github.singhalmradul.userservice.views;

import java.util.UUID;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.model.UserAccountDetails;

public record UserFullView(
    UUID id,
    String username,
    String displayName,
    String profilePictureUrl,
    String email,
    String bio
) implements UserView {
    public UserFullView(User user, UserAccountDetails accountDetails) {
        this(
            user.getId(),
            accountDetails.getUsername(),
            user.getDisplayName(),
            user.getProfilePictureUrl(),
            accountDetails.getEmail(),
            user.getBio()
        );
    }
}
