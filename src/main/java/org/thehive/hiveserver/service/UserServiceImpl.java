package org.thehive.hiveserver.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(@NonNull User user) {
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getUserInfo().setId(null);
        return userRepository.save(user);
    }

    @Override
    public User update(int id, @NonNull User user) throws EmptyResultDataAccessException {
        var userFromDb = findById(id);
        userFromDb.setUsername(user.getUsername());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
        userFromDb.getUserInfo().setFirstname(user.getUserInfo().getFirstname());
        userFromDb.getUserInfo().setFirstname(user.getUserInfo().getLastname());
        return userRepository.save(userFromDb);
    }

    @Override
    public User findById(int id) throws EmptyResultDataAccessException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException
                        (String.format("No class %s entity with id %d exists!", User.class.getName(), id), 1));
    }

    @Override
    public User findByUsername(@NonNull String username) throws EmptyResultDataAccessException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EmptyResultDataAccessException(
                        String.format("No class %s entity with username %s exists!", User.class.getName(), username), 1));
    }

    @Override
    public boolean existsByUsername(@NonNull String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(@NonNull String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(int id) throws EmptyResultDataAccessException {
        userRepository.deleteById(id);
    }

}
