package io.github.singhalmradul.userservice.projections;

import java.util.UUID;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.views.UserView;


public record UserDisplayViewProjection(
    UUID id,
    String displayName,
    String profilePictureUrl
) implements UserProjection, UserView {

    public UserDisplayViewProjection(User user) {
        this(
            user.getId(),
            user.getDisplayName(),
            user.getProfilePictureUrl()
        );
    }

    public UserDisplayViewProjection(UserAccountDetails accountDetails) {
        this(
            accountDetails.getUserId(),
            accountDetails.getUsername(),
            null
        );
    }
}