package io.github.singhalmradul.userservice.repositories;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.singhalmradul.userservice.model.User;
import io.github.singhalmradul.userservice.views.UserView;

public interface UserRepository extends JpaRepository<User, UUID> {

    <T extends UserView> Collection<T> findBy(Class<T> type);

    <T extends UserView> Optional<T> findById(UUID id, Class<T> type);

}
