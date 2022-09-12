package com.tacs.backend.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.tacs.backend.model.Match;
import org.springframework.data.domain.Page;

public interface MatchRepository extends RedisDocumentRepository<Match, String> {}
