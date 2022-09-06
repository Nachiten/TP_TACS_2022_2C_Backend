package com.tacs.backend.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.tacs.backend.model.Match;

public interface MatchRepository extends RedisDocumentRepository<Match, String> {}
