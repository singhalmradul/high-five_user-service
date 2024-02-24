package io.github.singhalmradul.userservice.views;

import java.util.UUID;

public record MinimalUser(UUID id, String username, String profilePictureUrl) implements UserView {

}