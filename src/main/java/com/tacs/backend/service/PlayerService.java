package com.tacs.backend.service;

import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.repository.MatchRepository;
import com.tacs.backend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
  @Autowired private PlayerRepository playerRepository;
  @Autowired private MatchRepository matchRepository;


  public int createPlayer(Player player) {

    Optional<Match> match = matchRepository.findById(player.getMatchId());

    if(match.isEmpty()){
        throw new EntityNotFoundException();
    }

    Player createdPlayer = playerRepository.save(player);
    return createdPlayer.getId();
  }
}
