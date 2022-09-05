package com.tacs.backend.service;

import com.tacs.backend.dto.StatisticsDTO;
import com.tacs.backend.dto.creation.MatchCreationDTO;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.repository.MatchRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
  @Autowired private MatchRepository matchRepository;

  public String createMatch(MatchCreationDTO match) {
    Match newMatch =
        new Match(match.getLocation(), match.getStartingDate(), match.getStartingTime());

    Match createdMatch = matchRepository.save(newMatch);

    return createdMatch.getId();
  }

  public Iterable<Match> getMatches() {
    return matchRepository.findAll();
  }

  public Match getMatch(String id) {
    Optional<Match> match = matchRepository.findById(id);

    if (match.isPresent()) {
      return match.get();
    } else {
      throw new EntityNotFoundException("Match not found");
    }
  }

  public void deleteMatch(String id) {
    Optional<Match> match = matchRepository.findById(id);

    if (match.isPresent()) {
      matchRepository.delete(match.get());
    } else {
      throw new EntityNotFoundException("Match not found");
    }
  }

  public StatisticsDTO getStatistics() {
    int hoursAgo = 2;

    // Get the count of matches created in the last two hours
    int matchesCreated =
        (int)
            StreamSupport.stream(matchRepository.findAll().spliterator(), false)
                .filter(
                    match ->
                        match.getCreationDate().isAfter(LocalDateTime.now().minusHours(hoursAgo)))
                .count();

    return new StatisticsDTO(matchesCreated);
  }
}
