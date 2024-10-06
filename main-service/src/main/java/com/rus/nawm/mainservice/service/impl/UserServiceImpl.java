package com.rus.nawm.mainservice.service.impl;

import com.rus.nawm.mainservice.domain.User;
import com.rus.nawm.mainservice.repository.UserRepository;
import com.rus.nawm.mainservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.findById(user.getId()).map(existingUser -> {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new NoSuchElementException("User with id: " + user.getId() + " wasn't found"));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}