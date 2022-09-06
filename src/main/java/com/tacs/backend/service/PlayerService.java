package com.tacs.backend.service;

import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.exception.ConflictException;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.MatchRepository;
import com.tacs.backend.repository.PlayerRepository;
import com.tacs.backend.repository.UserRepository;
import java.util.Optional;
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

    System.out.println("Regular player");

    Player newPlayer = getPlayer(playerCreation, regularPlayer);

    System.out.println("Get player");

    match.getPlayers().add(newPlayer);

    matchRepository.save(match);

    return playerRepository.save(newPlayer);
  }

  public Iterable<Player> getPlayers() {
    return playerRepository.findAll();
  }

  private Player getPlayer(PlayerCreationDTO playerCreation, boolean isRegular) {
    // Get user with email
    // Optional<User> user = userRepository.findByEmail(playerCreation.getUserEmail());

    Iterable<User> user = userRepository.findAll();

    // TODO - Horrible code, correct

    // Find user with email
    User userFound = null;

    for (User u : user) {
      if (u.getEmail().equals(playerCreation.getUserEmail())) {
        userFound = u;
      }
    }

    if (userFound != null) {
      System.out.println("User found, using...");

      // If player with email exists, use it
      String existingUserId = userFound.getId();

      return new Player(playerCreation.getMatchId(), existingUserId, isRegular);
    } else {
      System.out.println("User not found, creating...");

      // If player with email does not exist, create it
      User newUser = new User(playerCreation.getUserPhoneNumber(), playerCreation.getUserEmail());
      String createdUserId = userRepository.save(newUser).getId();

      return new Player(playerCreation.getMatchId(), createdUserId, isRegular);
    }
  }

  private Match getMatch(String matchId) {
    Optional<Match> match = matchRepository.findById(matchId);

    if (match.isEmpty()) {
      throw new EntityNotFoundException("Match with id " + matchId + " not found");
    }

    return match.get();
  }

  private boolean getPlayerIsRegular(Match match) {
    int playerCount = match.getPlayers().size();

    if (playerCount < 10) {
      return true;
    } else if (playerCount < 13) {
      return false;
    } else {
      throw new ConflictException("Match with id " + match.getId() + " is full");
    }
  }
}
