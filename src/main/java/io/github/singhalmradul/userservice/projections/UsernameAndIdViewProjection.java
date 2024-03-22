package io.github.singhalmradul.userservice.projections;

import java.util.UUID;

import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.views.UserView;

public record UsernameAndIdViewProjection(
    UUID id,
    String username
) implements UserProjection, UserView {

    public UsernameAndIdViewProjection(UserAccountDetails accountDetails) {
        this(
            accountDetails.getUserId(),
            accountDetails.getUsername()
        );
    }
}
