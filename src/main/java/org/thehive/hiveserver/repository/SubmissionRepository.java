package org.thehive.hiveserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thehive.hiveserver.entity.Submission;

public interface SubmissionRepository extends JpaRepository<Submission,Integer> {

}
