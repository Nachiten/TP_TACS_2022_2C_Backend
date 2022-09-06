package com.tacs.backend.service;

import com.tacs.backend.dto.PlayerStatisticsDTO;
import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.exception.ConflictException;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.MatchRepository;
import com.tacs.backend.repository.PlayerRepository;
import com.tacs.backend.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
  @Autowired private PlayerRepository playerRepository;
  @Autowired private MatchRepository matchRepository;

  @Autowired private UserRepository userRepository;

  public Player createPlayer(PlayerCreationDTO playerCreation) {

    Match match = getMatch(playerCreation.getMatchId());

    boolean regularPlayer = getPlayerIsRegular(match);

    Player newPlayer = generateNewPlayer(playerCreation, regularPlayer);

    match.getPlayers().add(newPlayer);
    matchRepository.save(match);

    return playerRepository.save(newPlayer);
  }

  public Iterable<Player> getPlayers() {
    return playerRepository.findAll();
  }

  private Player generateNewPlayer(PlayerCreationDTO playerCreation, boolean isRegular) {
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
      // If player with email exists, use it
      String existingUserId = userFound.getId();

      return new Player(playerCreation.getMatchId(), existingUserId, isRegular);
    } else {
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

  public Player getPlayer(String id) {
    Optional<Player> player = playerRepository.findById(id);

    if (player.isEmpty()) {
      throw new EntityNotFoundException("Match not found");
    }

    return player.get();
  }

  public PlayerStatisticsDTO getStatistics() {
    int hoursAgo = 2;

    // Get the count of players registered in the last two hours
    int playersRegistered =
        (int)
            StreamSupport.stream(playerRepository.findAll().spliterator(), false)
                .filter(
                    player ->
                        player.getCreationDate().isAfter(LocalDateTime.now().minusHours(hoursAgo)))
                .count();

    return new PlayerStatisticsDTO(playersRegistered);
  }
}
