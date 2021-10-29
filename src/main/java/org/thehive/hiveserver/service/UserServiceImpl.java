package org.thehive.hiveserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(int id, User user) {
        return null;
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

}
