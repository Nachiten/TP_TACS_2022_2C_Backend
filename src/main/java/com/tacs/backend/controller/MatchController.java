package com.tacs.backend.controller;

import com.tacs.backend.model.Match;
import com.tacs.backend.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchController {
  @Autowired MatchService matchService;

  @GetMapping()
  public Map<String, Match> getMatches() {
    return matchService.getMatches();
  }

  @Operation(summary = "Get a match by its id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Found the match"),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "match not found", content = @Content)
      })
  @GetMapping("/{id}")
  public Match getMatch(@PathVariable String id) {
    return matchService.getMatch(id);
  }

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public void createMatch(@Valid @RequestBody Match match) {
    matchService.createMatch(match);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteMatch(@PathVariable String id) {
    matchService.deleteMatch(id);
  }
}
