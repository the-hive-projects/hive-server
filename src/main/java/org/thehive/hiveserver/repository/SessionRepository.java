package org.thehive.hiveserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thehive.hiveserver.entity.Session;

public interface SessionRepository extends JpaRepository<Session,String> {

}
