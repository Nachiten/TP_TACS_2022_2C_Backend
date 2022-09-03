package com.tacs.backend.service;

import com.tacs.backend.model.Match;
import com.tacs.backend.repository.MatchRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
  @Autowired private MatchRepository matchRepository;

  public void createMatch(Match match) {
    matchRepository.save(match);
  }

  public Map<String, Match> getMatches() {
    return matchRepository.findAll();
  }

  public Match getMatch(String id) {
    return matchRepository.findById(id);
  }

  public void deleteMatch(String id) {
    matchRepository.delete(id);
  }
}
