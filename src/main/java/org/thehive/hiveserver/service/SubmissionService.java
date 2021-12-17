package org.thehive.hiveserver.service;

import org.thehive.hiveserver.entity.Submission;

import java.util.List;

public interface SubmissionService {

    Submission save(Submission submission);

    List<Submission> findAllBySessionId(int sessionId);

    List<Submission> findAllByUserId(int userId);

}
