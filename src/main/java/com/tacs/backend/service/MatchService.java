package com.tacs.backend.service;

import com.tacs.backend.dto.StatisticsDTO;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.repository.MatchRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
  @Autowired private MatchRepository matchRepository;

  public String createMatch(Match match) {
    Match createdMath = matchRepository.save(match);

    return createdMath.getId();
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
    //    int numberOfGames = 0;
    //    LocalDateTime now = LocalDateTime.now().minusHours(2);
    //
    //    for (Match match : getMatches()) {
    //      if (match.getCreationDate().isAfter(now)) {
    //        numberOfGames++;
    //      }
    //    }

    Stream<Match> stream =
        StreamSupport.stream(matchRepository.findAll().spliterator(), false)
            .filter(match -> match.getCreationDate().isAfter(LocalDateTime.now().minusHours(2)));

    return new StatisticsDTO((int) stream.count());
  }
}
