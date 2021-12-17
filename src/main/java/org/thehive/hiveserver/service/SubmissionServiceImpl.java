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
        return submissionRepository.save((Submission) submission.withId(null));
    }

    @Override
    public List<Submission> findAllBySessionId(int sessionId) {
        return submissionRepository.findAllBySession_Id(sessionId);
    }

    @Override
    public List<Submission> findAllByUserId(int userId) {
        return submissionRepository.findAllByUser_Id(userId);
    }

}
