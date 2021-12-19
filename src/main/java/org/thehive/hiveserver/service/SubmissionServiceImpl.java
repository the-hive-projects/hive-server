package org.thehive.hiveserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thehive.hiveserver.entity.Submission;
import org.thehive.hiveserver.repository.SubmissionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    @Override
    public Submission save(Submission submission) {
        submission.setId(null);
        return submissionRepository.save(submission);
    }

    @Override
    public List<Submission> findAllBySessionId(int sessionId) {
        return submissionRepository.findAllBySession_Id(sessionId);
    }

    @Override
    public List<Submission> findAllByUserId(int userId) {
        return submissionRepository.findAllByUser_Id(userId);
    }

    @Override
    public boolean containsByUserIdAndSessionId(int userId, int sessionId) {
        return submissionRepository.existsByUser_IdAndSession_Id(userId, sessionId);
    }

}
