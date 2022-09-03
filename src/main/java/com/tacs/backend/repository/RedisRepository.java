package com.tacs.backend.repository;

import com.tacs.backend.model.Match;
import java.util.Map;

public interface RedisRepository {
  Map<String, Match> findAll();

  Match findById(String id);

  void save(Match match);

  void delete(String id);
}
