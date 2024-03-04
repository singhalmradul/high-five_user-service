package io.github.singhalmradul.userservice.views;

import java.util.UUID;

/**
 * Represents a minimal user view.
 * This class provides a minimal set of user information including the user's ID, username, and profile picture URL.
 */
public record MinimalUser(UUID id, String username, String profilePictureUrl) implements UserView {

}