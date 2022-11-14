package com.tacs.backend;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tacs.backend.dto.creation.PlayerCreationDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("BackendTests")
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BackendApplicationControllerTests {

  @Autowired private MockMvc mockMvc;

  @Test
  public void _01_should_return_healthy() throws Exception {
    this.mockMvc
        .perform(get("/health"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Healthy!")));
  }

  @Test
  public void _02_should_return_404() throws Exception {
    this.mockMvc.perform(get("/non-existent")).andDo(print()).andExpect(status().isNotFound());
  }

  @Test
  public void _03_should_return_invalid_query_param() throws Exception {
    this.mockMvc
        .perform(get("/statistics/matches").queryParam("hours", "abc"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_QUERY_PARAM"));
  }

  @Test
  public void _04_should_create_match() throws Exception {

    String matchCreation = "{\"startingDateTime\": \"2023-09-17T21:18\",\"location\": \"abc\"}";

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches")
                .content(matchCreation)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void _05_should_create_new_player_regular() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(1234L, "jorgeabc@prueba.com");
    String matchId = "1";

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/" + matchId + "/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.phoneNumber").value(playerCreation.getPhoneNumber()))
        .andExpect(jsonPath("$.email").value(playerCreation.getEmail()))
        .andExpect(jsonPath("$.isRegular").value(true))
        .andExpect(jsonPath("$.matchId").value(matchId))
        .andExpect(jsonPath("$.creationDate").exists());
  }

  @Test
  public void _06_should_return_invalid_email() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(1234L, "jorgeabc");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(
            jsonPath("$.message")
                .value(containsString("email: must be a well-formed email address")));
  }

  @Test
  public void _07_should_return_not_empty_email() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(1234L, "");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(jsonPath("$.message").value(containsString("email: must not be empty")));
  }

  @Test
  public void _08_should_return_must_be_less_phoneNumber() throws Exception {
    PlayerCreationDTO playerCreation =
        new PlayerCreationDTO(9999999999999L, "jorgeabc2@prueba.com");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(
            jsonPath("$.message")
                .value(containsString("phoneNumber: must be less than or equal to 99999999999")));
  }

  @Test
  public void _09_should_return_must_be_greater_phoneNumber() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(911L, "jorgeabc2@prueba.com");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(
            jsonPath("$.message")
                .value(containsString("phoneNumber: must be greater than or equal to 1000")));
  }

  @Test
  public void _10_should_return_not_null_phoneNumber() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(null, "jorgeabc2@prueba.com");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(jsonPath("$.message").value(containsString("phoneNumber: must not be null")));
  }

  @Test
  public void _11_should_return_not_null_email() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(1561561L, null);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(jsonPath("$.message").value(containsString("email: must not be null")));
  }

  @Test
  public void _12_should_return_not_empty_phoneNumber() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(null, "jorgeabc2@prueba.com");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(jsonPath("$.message").value(containsString("phoneNumber: must not be null")));
  }

  @Test
  public void _13_should_return_player_already_exists_email() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(12345L, "jorgeabc@prueba.com");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.errorCode").value("PLAYER_EXISTENT"))
        .andExpect(jsonPath("$.message").value(containsString("Player with same phone number OR email already exists.")));
  }

  @Test
  public void _14_should_return_player_already_exists_phoneNumber() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(1234L, "jorgeabc@prueba.com.ar");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.errorCode").value("PLAYER_EXISTENT"))
        .andExpect(jsonPath("$.message").value(containsString("Player with same phone number OR email already exists.")));
  }

  @Test
  public void _15_should_return_match_not_found() throws Exception {
    PlayerCreationDTO playerCreation = new PlayerCreationDTO(12346L, "jorgeabc@prueba.com.ar");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches/2/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode").value("MATCH_NOT_FOUND"))
        .andExpect(jsonPath("$.message").value(containsString("Match not found")));
  }

  @Test
  public void _16_get_statistics_players() throws Exception {

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/statistics/players")
                .param("hours", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.playersEnrolled").value(1))
        .andExpect(jsonPath("$.now").exists());
  }

  @Test
  public void _17_get_statistics_matches() throws Exception {

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/statistics/matches")
                .param("hours", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.matchesCreated").value(1))
        .andExpect(jsonPath("$.now").exists());
  }

  @Test
  public void _18_get_statistics_players_0() throws Exception {

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/statistics/players")
                .param("hours", "0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.playersEnrolled").value(0))
        .andExpect(jsonPath("$.now").exists());
  }

  @Test
  public void _19_get_statistics_matches_0() throws Exception {

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/statistics/matches")
                .param("hours", "0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.matchesCreated").value(0))
        .andExpect(jsonPath("$.now").exists());
  }

  @Test
  public void _20_get_statistics_players_return_missing_params() throws Exception {

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/statistics/players")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("MISSING_QUERY_PARAM"))
        .andExpect(
            jsonPath("$.message")
                .value(containsString("'hours' parameter is missing of type int")));
  }

  @Test
  public void _21_get_statistics_matches_return_missing_params() throws Exception {

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/statistics/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("MISSING_QUERY_PARAM"))
        .andExpect(
            jsonPath("$.message")
                .value(containsString("'hours' parameter is missing of type int")));
  }

  @Test
  public void _22_should_return_startingDateTime_not_null() throws Exception {

    String matchCreation = "{\"startingDateTime\": null,\"location\": \"abc\"}";

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches")
                .content(matchCreation)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(
            jsonPath("$.message").value(containsString("startingDateTime: must not be null")));
    ;
  }

  @Test
  public void _23_should_return_location_not_null() throws Exception {

    String matchCreation = "{\"startingDateTime\": \"2023-09-17T21:18\",\"location\": null}";

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches")
                .content(matchCreation)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(jsonPath("$.message").value(containsString("location: must not be null")));
    ;
  }

  @Test
  public void _24_should_return_location_not_empty() throws Exception {

    String matchCreation = "{\"startingDateTime\": \"2023-09-17T21:18\",\"location\": \"\"}";

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches")
                .content(matchCreation)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
        .andExpect(jsonPath("$.message").value(containsString("location: must not be empty")));
    ;
  }

  @Test
  public void _25_should_return_location_not_empty() throws Exception {

    String matchCreation = "{\"startingDateTime\": \"fasgaggas\",\"location\": \"\"}";

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/matches")
                .content(matchCreation)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void _26_get_match() throws Exception {

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/matches/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.creationDate").exists())
        .andExpect(jsonPath("$.startingDateTime").exists())
        .andExpect(jsonPath("$.location").value("abc"));
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
