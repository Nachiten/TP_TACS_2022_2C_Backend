package com.tacs.backend;

import static io.restassured.RestAssured.DEFAULT_PORT;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.tacs.backend.dto.MatchesStatisticsDTO;
import com.tacs.backend.dto.PlayerStatisticsDTO;
import com.tacs.backend.dto.creation.MatchCreationDTO;
import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.service.MatchService;
import com.tacs.backend.service.PlayerService;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest()
class BackendApplicationTests {
  @Autowired private MatchService matchService;

  @Autowired private PlayerService playerService;


  static Match match1, match2, match3;
  static Player player1, player2, player3, player4, player5, player6, player7, player8, player9;
  static int matchesBefore, playersBefore;

  @Before
  public void setUp() {
    RestAssured.port = DEFAULT_PORT;
    matchService.deleteAll();
  }
  @Test
  void _0_initial_setup() {
    assertNotNull(matchService);
    assertNotNull(playerService);

    matchesBefore = (int) matchService.getMatches().spliterator().getExactSizeIfKnown();
    playersBefore = (int) playerService.getPlayers().spliterator().getExactSizeIfKnown();

    System.out.println("The DB has " + matchesBefore + " matches already stored");
    System.out.println("The DB has " + playersBefore + " players already stored");
  }



  @Test
  void _1_create_three_matches() {
    LocalDate date = LocalDate.now();
    LocalDateTime dateTime = LocalDateTime.now();

    match1 = matchService.createMatch(new MatchCreationDTO(date, dateTime, "Calle Corrientes"));
    match2 = matchService.createMatch(new MatchCreationDTO(date, dateTime, "Calle Camargo"));
    match3 = matchService.createMatch(new MatchCreationDTO(date, dateTime, "Calle Florida"));

    assertNotNull(match1);
    assertNotNull(match2);
    assertNotNull(match3);
  }

  @Test
  void _2_matches_correctly_created() {
    Match theMatch1 = matchService.getMatch(match1.getId());
    Match theMatch2 = matchService.getMatch(match2.getId());
    Match theMatch3 = matchService.getMatch(match3.getId());

    assertEquals(theMatch1, match1);
    assertEquals(theMatch2, match2);
    assertEquals(theMatch3, match3);
  }

  @Test
  void _3_add_three_players_to_each_match() {
    String match1ID = match1.getId();
    String match2ID = match2.getId();
    String match3ID = match3.getId();

    player1 =
        playerService.createPlayer(
            new PlayerCreationDTO(match1ID, "123465129", "juanperez@gmail.com"));
    player2 =
        playerService.createPlayer(new PlayerCreationDTO(match1ID, "456892", "pedrito@gmail.com"));
    player3 =
        playerService.createPlayer(new PlayerCreationDTO(match1ID, "5555", "rodri@gmail.com"));

    player4 =
        playerService.createPlayer(new PlayerCreationDTO(match2ID, "999853", "ramiro@gmail.com"));
    player5 =
        playerService.createPlayer(new PlayerCreationDTO(match2ID, "28483", "joaquin@gmail.com"));
    player6 =
        playerService.createPlayer(new PlayerCreationDTO(match2ID, "1231254", "camilo@gmail.com"));

    player7 =
        playerService.createPlayer(new PlayerCreationDTO(match3ID, "9999", "julieta@gmail.com"));
    player8 =
        playerService.createPlayer(new PlayerCreationDTO(match3ID, "8888", "romina@gmail.com"));
    player9 =
        playerService.createPlayer(new PlayerCreationDTO(match3ID, "7777", "jessica@gmail.com"));
  }

  @Test
  void _4_players_correctly_created() {
    Player thePlayer1 = playerService.getPlayer(player1.getId());
    Player thePlayer2 = playerService.getPlayer(player2.getId());
    Player thePlayer3 = playerService.getPlayer(player3.getId());
    Player thePlayer4 = playerService.getPlayer(player4.getId());
    Player thePlayer5 = playerService.getPlayer(player5.getId());
    Player thePlayer6 = playerService.getPlayer(player6.getId());
    Player thePlayer7 = playerService.getPlayer(player7.getId());
    Player thePlayer8 = playerService.getPlayer(player8.getId());
    Player thePlayer9 = playerService.getPlayer(player9.getId());

    assertEquals(thePlayer1, player1);
    assertEquals(thePlayer2, player2);
    assertEquals(thePlayer3, player3);
    assertEquals(thePlayer4, player4);
    assertEquals(thePlayer5, player5);
    assertEquals(thePlayer6, player6);
    assertEquals(thePlayer7, player7);
    assertEquals(thePlayer8, player8);
    assertEquals(thePlayer9, player9);
  }

  @Test
  void _5_get_player_statistics() {
    PlayerStatisticsDTO playerStatistics = playerService.getStatistics();

    assertEquals(playersBefore + 9, playerStatistics.getPlayersEnrolled());
  }

  @Test
  void _6_get_match_statistics() {
    MatchesStatisticsDTO match = matchService.getStatistics();

    assertEquals(matchesBefore + 3, match.getMatchesCreated());
  }

  @Test
  void _7_get_match_findAllPageable() throws Exception {

    RestAssured.baseURI= "http://localhost:3000/api";
    RestAssured.given().when().get("/matches/all"+ "?page=0&size=1").then().assertThat().statusCode(200);
    matchService.deleteAll();
  }
}
