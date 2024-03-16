package io.github.singhalmradul.userservice.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.github.singhalmradul.userservice.views.UserView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document(collection = "users")
@Data
public class User implements UserView{

    @Id
    private UUID id;

    @NotNull
    private String username;

    private String profilePictureUrl;

    private String displayName;

    @Email
    @NotNull
    private String email;

    private String bio;
}
