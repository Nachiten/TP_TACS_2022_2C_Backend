package com.tacs.backend;

import com.tacs.backend.dto.MatchesStatisticsDTO;
import com.tacs.backend.dto.PlayerStatisticsDTO;
import com.tacs.backend.dto.creation.MatchCreationDTO;
import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.exception.ConflictException;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.service.MatchService;
import com.tacs.backend.service.StatisticsService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("BackendTests")
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@WebAppConfiguration
class BackendApplicationTests {

    static Match match1, match2, match3;
    static Player player1, player2, player3, player4, player5, player6, player7, player8, player9;
    static int statisticsHours = 2;
    @Autowired
    private MatchService matchService;
    @Autowired
    private StatisticsService statisticsService;

    @Test
    void _1_create_three_matches() {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime tomorrow = dateTime.plusDays(1);

        match1 = matchService.createMatch(new MatchCreationDTO(tomorrow, "Calle Corrientes"));
        match2 = matchService.createMatch(new MatchCreationDTO(tomorrow, "Calle Camargo"));
        match3 = matchService.createMatch(new MatchCreationDTO(tomorrow, "Calle Florida"));

        assertNotNull(match1);
        assertNotNull(match2);
        assertNotNull(match3);
        assertEquals(tomorrow, match1.getStartingDateTime());
        assertEquals(tomorrow, match2.getStartingDateTime());
        assertEquals(tomorrow, match3.getStartingDateTime());
        assertEquals("Calle Corrientes", match1.getLocation());
        assertEquals("Calle Camargo", match2.getLocation());
        assertEquals("Calle Florida", match3.getLocation());
    }

    @Test
    void _2_create_match_with_invalid_date() {
        assertThrows(ConflictException.class, () -> {
            LocalDateTime dateTime = LocalDateTime.now();

            matchService.createMatch(new MatchCreationDTO(dateTime, "Calle Corrientes"));
        });
    }

    @Test
    void _3_add_three_players_to_each_match() {
        String match1ID = match1.getId();
        String match2ID = match2.getId();
        String match3ID = match3.getId();

        player1 = matchService.createPlayer(new PlayerCreationDTO(Long.parseLong("123465129"), "juanperez@gmail.com"), match1ID);
        player2 = matchService.createPlayer(new PlayerCreationDTO(Long.parseLong("456892"), "pedrito@gmail.com"), match1ID);
        player3 = matchService.createPlayer(new PlayerCreationDTO(Long.parseLong("5555"), "rodri@gmail.com"), match1ID);
        player4 = matchService.createPlayer(new PlayerCreationDTO(Long.parseLong("999853"), "ramiro@gmail.com"), match2ID);
        player5 = matchService.createPlayer(new PlayerCreationDTO(Long.parseLong("28483"), "joaquin@gmail.com"), match2ID);
        player6 = matchService.createPlayer(new PlayerCreationDTO(Long.parseLong("1231254"), "camilo@gmail.com"), match2ID);
        player7 = matchService.createPlayer(new PlayerCreationDTO(Long.parseLong("9999"), "julieta@gmail.com"), match3ID);
        player8 = matchService.createPlayer(new PlayerCreationDTO(Long.parseLong("8888"), "romina@gmail.com"), match3ID);
        player9 = matchService.createPlayer(new PlayerCreationDTO(Long.parseLong("7777"), "jessica@gmail.com"), match3ID);

        assertNotNull(player1);
        assertNotNull(player2);
        assertNotNull(player3);
        assertNotNull(player4);
        assertNotNull(player5);
        assertNotNull(player6);
        assertNotNull(player7);
        assertNotNull(player8);
        assertNotNull(player9);

        assertEquals(match1ID, player1.getMatchId());
        assertEquals(match1ID, player2.getMatchId());
        assertEquals(match1ID, player3.getMatchId());
        assertEquals(match2ID, player4.getMatchId());
        assertEquals(match2ID, player5.getMatchId());
        assertEquals(match2ID, player6.getMatchId());
        assertEquals(match3ID, player7.getMatchId());
        assertEquals(match3ID, player8.getMatchId());
        assertEquals(match3ID, player9.getMatchId());
    }

    @Test
    void _4_get_player_statistics_after_tests() {
        PlayerStatisticsDTO playerStatistics = statisticsService.getPlayersCreatedInLastHours(statisticsHours);

        assertEquals(9, playerStatistics.getPlayersEnrolled());
    }

    @Test
    void _5_get_match_statistics_after_tests() {
        MatchesStatisticsDTO match = statisticsService.getMatchesCreatedInLastHours(statisticsHours);

        assertEquals(3, match.getMatchesCreated());
    }

    @Test
    void _6_get_non_existent_match() {
        assertThrows(EntityNotFoundException.class, () -> {
            matchService.getMatch("nonExistantMatch");
        });
        assertThrows(EntityNotFoundException.class, () -> {
            matchService.getMatch("nonExistantMatch2");
        });
        assertThrows(EntityNotFoundException.class, () -> {
            matchService.getMatch("nonExistantMatch3");
        });
    }

    @Test
    void _7_create_existent_matches() {
        assertThrows(ConflictException.class, () ->
            matchService.createMatch(new MatchCreationDTO(match1.getStartingDateTime(), match1.getLocation()))
        );
        assertThrows(ConflictException.class, () ->
            matchService.createMatch(new MatchCreationDTO(match2.getStartingDateTime(), match2.getLocation()))
        );
        assertThrows(ConflictException.class, () ->
            matchService.createMatch(new MatchCreationDTO(match3.getStartingDateTime(), match3.getLocation()))
        );
    }

    @Test
    void _8_create_existent_players() {
        assertThrows(ConflictException.class, () -> matchService.createPlayer(new PlayerCreationDTO(player1.getPhoneNumber(), player1.getEmail()), match1.getId()));
        assertThrows(ConflictException.class, () -> matchService.createPlayer(new PlayerCreationDTO(player4.getPhoneNumber(), player4.getEmail()), match2.getId()));
        assertThrows(ConflictException.class, () -> matchService.createPlayer(new PlayerCreationDTO(player7.getPhoneNumber(), player7.getEmail()), match3.getId()));
    }

    @Test
    void _9_create_player_with_invalid_match() {
        assertThrows(EntityNotFoundException.class, () ->
            matchService.createPlayer(
                new PlayerCreationDTO(Long.parseLong("123465129"),
                    "email"), "nonExistantMatch"));
    }

    // TODO - First 10 players to join a match are regular

    // TODO - Players 11 to 13 are substitutes

    // TODO - Player 14 is not allowed to join the match
}
