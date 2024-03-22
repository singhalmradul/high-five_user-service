package io.github.singhalmradul.userservice.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.github.singhalmradul.userservice.projections.UserProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements UserProjection {

    @Id
    private UUID id;

    private String profilePictureUrl;

    private String displayName;

    private String bio;
}
