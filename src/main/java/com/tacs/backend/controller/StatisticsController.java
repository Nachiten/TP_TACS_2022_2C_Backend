package com.tacs.backend.controller;

import com.tacs.backend.dto.MatchesStatisticsDTO;
import com.tacs.backend.dto.PlayerStatisticsDTO;
import com.tacs.backend.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
  @Autowired StatisticsService statisticsService;

  @Operation(summary = "Get the amount of players enrolled in the last X hours")
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
  @GetMapping("/players")
  public PlayerStatisticsDTO getPlayerStatistics(@Valid @RequestParam int hours) {
    return statisticsService.getPlayersCreatedInLastHours(hours);
  }

  @Operation(summary = "Get the amount of matches created in the last X hours")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content =
                @Content(
                    schema = @Schema(implementation = MatchesStatisticsDTO.class),
                    mediaType = "application/json"))
      })
  @GetMapping("/matches")
  public MatchesStatisticsDTO getMatchesStatistics(@Valid @RequestParam int hours) {
    return statisticsService.getMatchesCreatedInLastHours(hours);
  }
}
