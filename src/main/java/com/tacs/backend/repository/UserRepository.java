package com.tacs.backend.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.tacs.backend.model.User;

public interface UserRepository extends RedisDocumentRepository<User, String> {}
