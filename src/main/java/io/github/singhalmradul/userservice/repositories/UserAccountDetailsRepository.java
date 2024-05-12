package io.github.singhalmradul.userservice.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.singhalmradul.userservice.model.UserAccountDetails;

public interface UserAccountDetailsRepository extends JpaRepository<UserAccountDetails, UUID> {}
