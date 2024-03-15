package io.github.singhalmradul.userservice.model;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.github.singhalmradul.userservice.views.UserView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document(collection = "users")
@Data
public class User implements Serializable, UserView{

    @Id
    private UUID id;

    @NotNull
    private String username;

    private String profilePictureUrl;

    @NotNull
    private String displayName;

    @Email
    @NotNull
    private String email;

}
