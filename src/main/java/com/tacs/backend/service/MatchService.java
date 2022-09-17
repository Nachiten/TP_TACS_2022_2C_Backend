package com.tacs.backend.service;

import com.tacs.backend.dto.creation.MatchCreationDTO;
import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.exception.ConflictException;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.repository.MatchRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
  @Autowired private MatchRepository matchRepository;

  public Match createMatch(MatchCreationDTO match) {
    Match newMatch = new Match(match.getLocation(), match.getStartingDateTime());

    Iterable<Match> matches = getMatches();

    if (StreamSupport.stream(matches.spliterator(), false).anyMatch(m -> m.equals(newMatch)))
      throw new ConflictException("The match already exists.", "MATCH_EXISTENT");

    if (newMatch.getStartingDateTime().isBefore(LocalDateTime.now()))
      throw new ConflictException("The match starting date is before now.", "INVALID_MATCH_DATE");

    return matchRepository.save(newMatch);
  }

  public Iterable<Match> getMatches() {
    return matchRepository.findAll();
  }

  public Match getMatch(String id) {
    Optional<Match> match = matchRepository.findById(id);

    if (match.isEmpty()) {
      throw new EntityNotFoundException("Match not found", "MATCH_NOT_FOUND");
    }

    return match.get();
  }

  public void deleteMatch(String id) {
    Optional<Match> match = matchRepository.findById(id);

    if (match.isEmpty()) throw new EntityNotFoundException("Match not found", "MATCH_NOT_FOUND");

    matchRepository.delete(match.get());
  }

  public Player createPlayer(PlayerCreationDTO playerCreation, String matchId) {

    Match match = getMatch(matchId);

    boolean regularPlayer = getPlayerIsRegular(match);

    Player newPlayer =
        new Player(
            matchId, playerCreation.getPhoneNumber(), playerCreation.getEmail(), regularPlayer);

    checkPlayerExistent(newPlayer, match);

    match.addPlayer(newPlayer);
    matchRepository.save(match);

    return newPlayer;
  }

  private void checkPlayerExistent(Player player, Match match) {
    if (match.hasPlayer(player)) {
      throw new ConflictException("Player already exists", "PLAYER_EXISTENT");
    }
  }

  private boolean getPlayerIsRegular(Match match) {
    int playerCount = match.getPlayers().size();

    if (playerCount < 10) return true;
    else if (playerCount < 13) return false;
    else throw new ConflictException("Match with id " + match.getId() + " is full", "MATCH_FULL");
  }
}
