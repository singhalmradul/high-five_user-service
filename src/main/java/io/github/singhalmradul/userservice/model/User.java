package io.github.singhalmradul.userservice.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class User {

    @Id
    private UUID id;

    private String avatar;

    private String displayName;

    private String bio;
}
