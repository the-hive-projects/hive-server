package org.thehive.hiveserver.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.thehive.hiveserver.entity.Submission;
import org.thehive.hiveserver.repository.SubmissionRepository;

@Server
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    @Override
    public Submission save(Submission submission) {
        return submissionRepository.save(submission);
    }

}
