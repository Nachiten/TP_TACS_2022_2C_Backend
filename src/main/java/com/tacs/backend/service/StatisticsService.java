package com.tacs.backend.service;

import com.tacs.backend.dto.MatchesStatisticsDTO;
import com.tacs.backend.dto.PlayerStatisticsDTO;
import com.tacs.backend.model.Match;
import com.tacs.backend.repository.MatchRepository;

import java.time.LocalDateTime;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    MatchRepository matchRepository;

    public PlayerStatisticsDTO getPlayersCreatedInLastHours(int hours) {

        // Get all matches, merge all players in one list
        Iterable<Match> matches = matchRepository.findAll();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hoursAgo = now.minusHours(hours);

        long players =
            StreamSupport.stream(matches.spliterator(), false)
                .flatMap(match -> match.getPlayers().stream())
                .distinct()
                .filter(player -> player.getCreationDate().isAfter(hoursAgo))
                .count();

        return new PlayerStatisticsDTO(players, now);
    }

    public MatchesStatisticsDTO getMatchesCreatedInLastHours(int hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hoursAgo = now.minusHours(hours);

        // Get the count of matches created in the last hours
        long matchesCreated =
            StreamSupport.stream(matchRepository.findAll().spliterator(), false)
                .filter(match -> match.getCreationDate().isAfter(hoursAgo))
                .count();

        return new MatchesStatisticsDTO(matchesCreated, now);
    }
}
