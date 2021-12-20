package org.thehive.hiveserver.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.thehive.hiveserver.entity.Submission;

import java.util.List;

public interface SubmissionService {

    Submission save(Submission submission);

    List<Submission> findAllBySessionId(int sessionId);

    List<Submission> findAllByUserId(int userId);

    Submission findByUserIdAndSessionId(int userId, int sessionId) throws EmptyResultDataAccessException;

    boolean containsByUserIdAndSessionId(int userId, int sessionId);

}
