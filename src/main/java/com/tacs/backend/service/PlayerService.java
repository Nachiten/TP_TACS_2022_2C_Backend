package com.tacs.backend.service;

import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.exception.ConflictException;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.MatchRepository;
import com.tacs.backend.repository.PlayerRepository;
import java.util.Optional;

import com.tacs.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
  @Autowired private PlayerRepository playerRepository;
  @Autowired private MatchRepository matchRepository;

  @Autowired private UserRepository userRepository;

  public Player createPlayer(PlayerCreationDTO playerCreation) {

    Match match = getMatch(playerCreation.getMatchId());

    System.out.println("Match found...");

    boolean regularPlayer = getPlayerIsRegular(match);

    Player newPlayer = getPlayer(playerCreation, regularPlayer);

    return playerRepository.save(newPlayer);
  }

  private Player getPlayer(PlayerCreationDTO playerCreation, boolean isRegular){
    // Get user with email
    Optional<User> user = userRepository.findByEmail(playerCreation.getUserEmail());

    if (user.isPresent()){
      System.out.println("User found, using...");

      // If player with email exists, use it
      String existingUserId = user.get().getId();

      return new Player(playerCreation.getMatchId(), existingUserId, isRegular);
    } else {
      System.out.println("User not found, creating...");

      // If player with email does not exist, create it
      User newUser = new User(playerCreation.getUserPhoneNumber(), playerCreation.getUserEmail());
      String createdUserId = userRepository.save(newUser).getId();

      return new Player(playerCreation.getMatchId(), createdUserId, isRegular);
    }
  }

  private Match getMatch(String matchId){
    Optional<Match> match = matchRepository.findById(matchId);

    if (match.isEmpty()) {
      throw new EntityNotFoundException("Match with id " + matchId + " not found");
    }

    return match.get();
  }

  private boolean getPlayerIsRegular(Match match){
    int playerCount = match.getPlayers().size();

    if (playerCount < 10){
      return true;
    } else if (playerCount < 13){
      return false;
    } else {
        throw new ConflictException("Match with id " + match.getId() + " is full");
    }
  }
}
