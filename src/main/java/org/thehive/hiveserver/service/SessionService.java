package org.thehive.hiveserver.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.thehive.hiveserver.entity.Session;

public interface SessionService {

    Session save(Session session);

    Session findById(Integer id) throws EmptyResultDataAccessException;

    void deleteById(Integer id) throws EmptyResultDataAccessException;

}
