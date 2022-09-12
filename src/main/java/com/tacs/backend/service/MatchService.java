package com.tacs.backend.service;

import com.tacs.backend.dto.MatchesStatisticsDTO;
import com.tacs.backend.dto.creation.MatchCreationDTO;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.repository.MatchRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
  @Autowired private MatchRepository matchRepository;

  public Match createMatch(MatchCreationDTO match) {
    Match newMatch =
        new Match(match.getLocation(), match.getStartingDate(), match.getStartingTime());

    return matchRepository.save(newMatch);
  }

  public Iterable<Match> getMatches() {
    return matchRepository.findAll();
  }


  public Page<Match> getMatchesPageable(Pageable pageable) throws Exception {
    try {
      Page matchslist = matchRepository.findAll(pageable);
      return matchslist;
    }catch (Exception e) {
      throw new Exception(e.getMessage());

    }

  }

  public Match getMatch(String id) {
    Optional<Match> match = matchRepository.findById(id);

    if (match.isEmpty()) {
      throw new EntityNotFoundException("Match not found");
    }

    return match.get();
  }

  public void deleteMatch(String id) {
    Optional<Match> match = matchRepository.findById(id);

    if (match.isEmpty()) {
      throw new EntityNotFoundException("Match not found");
    }

    matchRepository.delete(match.get());
  }

  public MatchesStatisticsDTO getStatistics() {
    int hoursAgo = 2;

    // Get the count of matches created in the last two hours
    int matchesCreated =
        (int)
            StreamSupport.stream(matchRepository.findAll().spliterator(), false)
                .filter(
                    match ->
                        match.getCreationDate().isAfter(LocalDateTime.now().minusHours(hoursAgo)))
                .count();

    return new MatchesStatisticsDTO(matchesCreated);
  }




  public void deleteAll(){
    matchRepository.deleteAll();
  }




}
