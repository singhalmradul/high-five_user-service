package io.github.singhalmradul.userservice.model;

import static jakarta.persistence.GenerationType.UUID;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_account_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDetails {

    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;
}
