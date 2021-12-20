package org.thehive.hiveserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return submissionRepository.findAllBySessionId(sessionId);
    }

    @Override
    public List<Submission> findAllByUserId(int userId) {
        return submissionRepository.findAllByUserId(userId);
    }

    @Override
    public Submission findByUserIdAndSessionId(int userId, int sessionId) throws EmptyResultDataAccessException {
        return submissionRepository.findByUserIdAndSessionId(userId, sessionId)
                .orElseThrow(() -> new EmptyResultDataAccessException
                        (String.format("No class %s entity with userId %s and sessionId: %s exists!", Submission.class.getName(), userId, sessionId), 1));
    }

    @Override
    public boolean containsByUserIdAndSessionId(int userId, int sessionId) {
        return submissionRepository.existsByUserIdAndSessionId(userId, sessionId);
    }

}
