package org.thehive.hiveserver.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.thehive.hiveserver.entity.Session;

import java.util.List;

public interface SessionService {

    Session save(Session session);

    List<Session> findAllByUserId(int userId);

    boolean containsByIdAndUserId(int id, int userId);

    void deleteById(Integer id) throws EmptyResultDataAccessException;

}
