package io.github.singhalmradul.userservice.events;

import lombok.Data;

@Data
public abstract class Event<T> {
    private final T payload;
}
