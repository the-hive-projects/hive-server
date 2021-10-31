package org.thehive.hiveserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thehive.hiveserver.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
