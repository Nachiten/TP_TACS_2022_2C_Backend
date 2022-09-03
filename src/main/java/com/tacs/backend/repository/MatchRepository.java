package com.tacs.backend.repository;

import com.tacs.backend.model.Match;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

@Repository
public class MatchRepository implements RedisRepository {
  private static final String KEY = "Match";

  private final RedisTemplate<String, Match> redisTemplate;
  private HashOperations hashOperations;

  public MatchRepository(RedisTemplate<String, Match> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @PostConstruct
  private void init() {
    hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public List<Match> findAll() {
    ArrayList<Match> matches = new ArrayList<Match>();
    Cursor<Map.Entry<Object, Object>> c =
        hashOperations.scan(KEY, ScanOptions.scanOptions().build());
    while (c.hasNext()) {
      Map.Entry<Object, Object> keyvalue = c.next();
      Match match = (Match) keyvalue.getValue();
      match.setId(keyvalue.getKey().toString());
      matches.add(match);
    }

    return matches;
  }

  @Override
  public Match findById(String id) {
    return (Match) hashOperations.get(KEY, id);
  }

  @Override
  public void save(Match match) {
    hashOperations.put(KEY, UUID.randomUUID().toString(), match);
  }

  @Override
  public void delete(String id) {
    hashOperations.delete(KEY, id);
  }
}
