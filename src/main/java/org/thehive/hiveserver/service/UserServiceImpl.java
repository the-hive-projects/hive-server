package org.thehive.hiveserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.repository.UserRepository;

import javax.persistence.PersistenceException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        var userToDb=user.withId(0);
        userToDb.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userToDb);
    }

    @Override
    public User update(int id, User user) {
        var userFromDbOptional=userRepository.findById(id);
        var userFromDb=userFromDbOptional.get();
        userFromDb.setUsername(user.getUsername());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userFromDb);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

}
