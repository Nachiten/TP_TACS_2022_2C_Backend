package com.tacs.backend.service;

import com.tacs.backend.dto.MatchesStatisticsDTO;
import com.tacs.backend.dto.PlayerStatisticsDTO;
import com.tacs.backend.model.Match;
import com.tacs.backend.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.StreamSupport;

@Service
public class StatisticsService {

    @Autowired
    MatchRepository matchRepository;

    public PlayerStatisticsDTO getPlayersCreatedInLastHours(int hours) {

        // Get all matches, merge all players in one list
        Iterable<Match> matches = matchRepository.findAll();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minTime = now.minusHours(hours);

        Long players = matchRepository.countAllPlayersInAllMatchesDateGreaterThan(minTime);

        // DB Returns null in case there are 0 players
        if (players == null) {
            players = 0L;
        }

        return new PlayerStatisticsDTO(players, now);
    }

    public MatchesStatisticsDTO getMatchesCreatedInLastHours(int hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minTime = now.minusHours(hours);

        Iterable<Match> matches = matchRepository.findAllByCreationDateGreaterThan(minTime);

        long matchesCount = StreamSupport.stream(matches.spliterator(), false).count();

        return new MatchesStatisticsDTO(matchesCount, now);
    }
}
