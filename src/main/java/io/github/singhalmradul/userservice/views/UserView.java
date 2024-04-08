package io.github.singhalmradul.userservice.views;

import java.io.Serializable;
import java.util.UUID;

public interface UserView extends Serializable {
    UUID id();
}
