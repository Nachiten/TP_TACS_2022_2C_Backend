package com.tacs.backend.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.tacs.backend.model.User;

import java.util.Optional;

public interface UserRepository extends RedisDocumentRepository<User, String> {
    Optional<User> findByEmail(String email);
}
