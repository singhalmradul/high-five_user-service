package io.github.singhalmradul.userservice.events;

import io.github.singhalmradul.userservice.model.UserAccountDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserAccountCreationEvent extends Event<UserAccountDetails> {
    public UserAccountCreationEvent(UserAccountDetails payload) {
        super(payload);
    }
}
