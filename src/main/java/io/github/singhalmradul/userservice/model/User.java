package io.github.singhalmradul.userservice.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

import io.github.singhalmradul.userservice.views.UserViews;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_record")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonView(UserViews.Minimal.class)
    private UUID id;

    @Column(nullable = false)
    @JsonView(UserViews.Minimal.class)
    private String username;

    @JsonView(UserViews.Minimal.class)
    private String profilePicture;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
