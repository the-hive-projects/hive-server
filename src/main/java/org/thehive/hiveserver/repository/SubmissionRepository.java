package org.thehive.hiveserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thehive.hiveserver.entity.Submission;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    List<Submission> findAllBySession_Id(int sessionId);

    List<Submission> findAllByUser_Id(int userId);

    boolean existsByUser_IdAndSession_Id(int userId, int sessionId);

}
