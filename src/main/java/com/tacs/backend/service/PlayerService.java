package com.tacs.backend.service;

import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.repository.MatchRepository;
import com.tacs.backend.repository.PlayerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
  @Autowired private PlayerRepository playerRepository;
  @Autowired private MatchRepository matchRepository;

  public Player createPlayer(Player player) {

    // Check that match exists
    Optional<Match> match = matchRepository.findById(player.getMatchId());

    if (match.isEmpty()) {
      throw new EntityNotFoundException("Match with id " + player.getMatchId() + " not found");
    }

    // TODO - Check that user exists, or create new user

    return playerRepository.save(player);
  }
}
