package com.tacs.backend.service;

import com.tacs.backend.dto.MatchesStatisticsDTO;
import com.tacs.backend.dto.PlayerStatisticsDTO;
import com.tacs.backend.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatisticsService {

    @Autowired
    MatchRepository matchRepository;

    public PlayerStatisticsDTO getPlayersCreatedInLastHours(int hours) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minTime = now.minusHours(hours);

        Long players = matchRepository.countAllPlayersInAllMatchesDateGreaterThan(minTime);

        if (players == null) {
            players = 0L;
        }

        return new PlayerStatisticsDTO(players, now);
    }

    public MatchesStatisticsDTO getMatchesCreatedInLastHours(int hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minTime = now.minusHours(hours);

        Long matches = matchRepository.countAllMatchesCreatedDateGreaterThan(minTime);

        if (matches == null) {
            matches = 0L;
        }

        return new MatchesStatisticsDTO(matches, now);
    }
}
