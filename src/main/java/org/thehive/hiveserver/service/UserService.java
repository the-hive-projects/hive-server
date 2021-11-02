package org.thehive.hiveserver.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.thehive.hiveserver.entity.User;

public interface UserService {

    User save(User user);

    User update(int id, User user) throws EmptyResultDataAccessException ;

    User findById(int id) throws EmptyResultDataAccessException;

    User findByUsername(String username) throws EmptyResultDataAccessException;

    void deleteById(int id) throws EmptyResultDataAccessException;

}
