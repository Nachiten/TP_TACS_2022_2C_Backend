package com.tacs.backend.repository;

import com.tacs.backend.domain.Match;
import org.springframework.stereotype.Repository;

import java.util.Map;

//implements  RedisRepository extends CrudRepository<Match, String>

public interface RedisRepository {
    Map<String, Match> findAll();
    Match findById(String id);
    void save(Match match);
    void delete(String id);

}
