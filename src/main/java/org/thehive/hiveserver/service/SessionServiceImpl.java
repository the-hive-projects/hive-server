package org.thehive.hiveserver.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.thehive.hiveserver.entity.Session;
import org.thehive.hiveserver.repository.SessionRepository;
import org.thehive.hiveserver.session.SessionIdGenerator;

@RequiredArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final SessionIdGenerator sessionIdGenerator;

    @Override
    public Session save(@NonNull Session session) {
        return sessionRepository.save(session.withId(sessionIdGenerator.generate()));
    }

    @Override
    public Session findById(@NonNull String id) throws EmptyResultDataAccessException {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException
                        (String.format("No class %s entity with id %s exists!", Session.class.getName(), id), 1));
    }

    @Override
    public void deleteById(@NonNull String id) throws EmptyResultDataAccessException {
        sessionRepository.deleteById(id);
    }

}