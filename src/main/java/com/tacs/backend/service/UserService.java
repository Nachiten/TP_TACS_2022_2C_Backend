package com.tacs.backend.service;

import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired private UserRepository userRepository;

  public String createUser(User user) {
    User createdMath = userRepository.save(user);

    return createdMath.getId();
  }

  public Iterable<User> getUsers() {
    return userRepository.findAll();
  }

  public User getUser(String id) {
    Optional<User> user = userRepository.findById(id);

    if (user.isPresent()) {
      return user.get();
    } else {
      throw new EntityNotFoundException("User not found");
    }
  }

  public void deleteUser(String id) {
    Optional<User> user = userRepository.findById(id);

    if (user.isPresent()) {
      userRepository.delete(user.get());
    } else {
      throw new EntityNotFoundException("User not found");
    }
  }
}
