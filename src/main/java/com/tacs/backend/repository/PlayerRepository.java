package com.tacs.backend.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.tacs.backend.model.Player;

public interface PlayerRepository extends RedisDocumentRepository<Player, String> {}
