package org.thehive.hiveserver.service;

import org.thehive.hiveserver.entity.User;

public interface UserService {

    User save(User user);

    User update(int id,User user);

    User findById(int id);

    void deleteById(int id);

}
