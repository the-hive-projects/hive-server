package org.thehive.hiveserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thehive.hiveserver.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
