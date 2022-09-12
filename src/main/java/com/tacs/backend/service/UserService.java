package com.tacs.backend.service;

import com.tacs.backend.dto.creation.UserCreationDTO;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired private UserRepository userRepository;

  public User createUser(UserCreationDTO user) {
    User newuser = new User(user.getPhoneNumber(), user.getEmail());

    return userRepository.save(newuser);
  }

  public Iterable<User> getUseres() {
    return userRepository.findAll();
  }

  public Iterable<User> getUseresPageable(Pageable page) {
    return userRepository.findAll(page);
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
