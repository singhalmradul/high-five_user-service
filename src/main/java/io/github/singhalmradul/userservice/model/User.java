package io.github.singhalmradul.userservice.model;

import static jakarta.persistence.GenerationType.UUID;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import io.github.singhalmradul.userservice.views.UserView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
public class User implements Serializable, UserView{

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @Column(nullable = false)
    private String username;

    private String profilePictureUrl;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
