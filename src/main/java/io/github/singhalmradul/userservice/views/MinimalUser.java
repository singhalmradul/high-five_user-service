package io.github.singhalmradul.userservice.views;

import java.util.UUID;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.model.UserAccountDetails;

/**
 * Represents a minimal user view.
 * This class provides a minimal set of user information including the user's ID, displayName, and
 * profile picture URL.
 */
public record MinimalUser(UUID id, String displayName, String profilePictureUrl) implements UserView {

    public MinimalUser(UserAccountDetails accountDetails, User user) {

        this(
            accountDetails.getUserId(),
            user.getDisplayName() == null ? accountDetails.getUsername() : user.getDisplayName(),
            user.getProfilePictureUrl()
        );
    }
}