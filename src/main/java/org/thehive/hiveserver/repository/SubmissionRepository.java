package org.thehive.hiveserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thehive.hiveserver.entity.Submission;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    List<Submission> findAllBySessionId(int sessionId);

    List<Submission> findAllByUserId(int userId);

    Optional<Submission> findByUserIdAndSessionId(int userId, int sessionId);

    boolean existsByUserIdAndSessionId(int userId, int sessionId);

}
