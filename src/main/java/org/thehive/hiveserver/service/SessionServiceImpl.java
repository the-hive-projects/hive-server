package org.thehive.hiveserver.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.thehive.hiveserver.entity.Session;
import org.thehive.hiveserver.repository.SessionRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public Session save(@NonNull Session session) {
        session.setId(null);
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> findAllByUserId(@NonNull int userId) {
        return sessionRepository.findAllByUserId(userId);
    }

    @Override
    public boolean containsByIdAndUserId(int id, int userId) {
        return sessionRepository.existsByIdAndUserId(id, userId);
    }

    @Override
    public void deleteById(@NonNull Integer id) throws EmptyResultDataAccessException {
        sessionRepository.deleteById(id);
    }

}
