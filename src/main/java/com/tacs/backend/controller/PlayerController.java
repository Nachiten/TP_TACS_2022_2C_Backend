package com.tacs.backend.controller;

import com.tacs.backend.dto.ExceptionDTO;
import com.tacs.backend.dto.PlayerStatisticsDTO;
import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.model.Player;
import com.tacs.backend.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class PlayerController {
  @Autowired PlayerService playerService;

  @Operation(summary = "Get all players")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the players",
            content =
                @Content(
                    array = @ArraySchema(schema = @Schema(implementation = Player.class)),
                    mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content())
      })
  @GetMapping()
  public Iterable<Player> getPlayers() {
    return playerService.getPlayers();
  }

  @Operation(summary = "Create a new player")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content =
                @Content(
                    schema = @Schema(implementation = Player.class),
                    mediaType = "application/json")),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid body",
            content =
                @Content(
                    schema = @Schema(implementation = ExceptionDTO.class),
                    mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content)
      })
  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public Player createPlayer(@Valid @RequestBody PlayerCreationDTO player) {
    return playerService.createPlayer(player);
  }

  @Operation(summary = "Get the amount of players enrolled in the last two hours")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content =
                @Content(
                    schema = @Schema(implementation = PlayerStatisticsDTO.class),
                    mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content)
      })
  @GetMapping("/statistics")
  public PlayerStatisticsDTO getStatistics() {
    return playerService.getStatistics();
  }
}
