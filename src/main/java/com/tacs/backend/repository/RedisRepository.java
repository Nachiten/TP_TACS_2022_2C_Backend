package com.tacs.backend.repository;

import com.tacs.backend.model.Match;
import java.util.List;

public interface RedisRepository {
  List<Match> findAll();

  Match findById(String id);

  void save(Match match);

  void delete(String id);
}
