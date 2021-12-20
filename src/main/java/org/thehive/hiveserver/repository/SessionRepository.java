package org.thehive.hiveserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thehive.hiveserver.entity.Session;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    List<Session> findAllByUserId(int userId);

    boolean existsByIdAndUserId(int id, int userId);

}
