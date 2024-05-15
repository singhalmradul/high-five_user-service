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
    String avatar,
    String displayName,
    String bio,
    boolean isFollowed
) implements UserView {

    public CompleteUser(
            UserAccountDetails accountDetails,
            User user,
            boolean isFollowed
        ) {

        this(
            accountDetails.getUserId(),
            accountDetails.getUsername(),
            accountDetails.getEmail(),
            user.getAvatar(),
            user.getDisplayName(),
            user.getBio(),
            isFollowed
        );
    }

    public User toUser() {
        return User.builder()
            .avatar(avatar)
            .displayName(displayName)
            .bio(bio)
            .build();
    }
}
