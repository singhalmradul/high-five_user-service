package io.github.singhalmradul.userservice.model;

import java.util.UUID;

public record MinimalUser(UUID id, String username, String profilePicture) {
}