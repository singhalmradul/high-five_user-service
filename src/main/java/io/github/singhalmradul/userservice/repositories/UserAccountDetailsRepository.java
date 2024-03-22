package io.github.singhalmradul.userservice.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.singhalmradul.userservice.model.UserAccountDetails;
import io.github.singhalmradul.userservice.projections.UserAccountDetailsProjection;


public interface UserAccountDetailsRepository extends JpaRepository<UserAccountDetails, UUID> {

    <T extends UserAccountDetailsProjection> T findByUsername(String username, Class<T> type);

    <T extends UserAccountDetailsProjection> T findById(UUID id, Class<T> type);
}
