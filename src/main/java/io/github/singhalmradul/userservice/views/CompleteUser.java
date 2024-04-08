package io.github.singhalmradul.userservice.views;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import lombok.Builder;

@Builder
public record CompleteUser(
    UUID id,
    String username,
    String email,
    String avatar,
    String displayName,
    String bio,
    List<IdOnly> followers,
    List<IdOnly> following
) implements UserView {

    public CompleteUser(
        UserAccountDetails accountDetails,
        User user
    ) {
        this(
            accountDetails.getUserId(),
            accountDetails.getUsername(),
            accountDetails.getEmail(),
            user.getAvatar(),
            user.getDisplayName(),
            user.getBio(),
            new ArrayList<>(),
            new ArrayList<>()
        );
    }

}
