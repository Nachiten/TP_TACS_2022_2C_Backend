package com.tacs.backend.service;

import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Iterable<User> getUseres() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        Optional<User> User = userRepository.findById(id);

        if (User.isPresent()) {
            return User.get();
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public void deleteUser(String id) {
        Optional<User> User = userRepository.findById(id);

        if (User.isPresent()) {
            userRepository.delete(User.get());
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
}
