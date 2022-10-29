package com.tacs.backend.controller;

import com.tacs.backend.dto.ExceptionDTO;
import com.tacs.backend.dto.creation.MatchCreationDTO;
import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.service.MatchService;
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
@RequestMapping("/matches")
public class MatchController {
    @Autowired
    MatchService matchService;

    @Operation(summary = "Get all matches")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Found the matches",
                content =
                @Content(
                    array = @ArraySchema(schema = @Schema(implementation = Match.class)),
                    mediaType = "application/json")),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content())
        })
    @GetMapping()
    public Iterable<Match> getMatches() {
        return matchService.getMatches();
    }

    @Operation(summary = "Get a match by its id")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Found the match",
                content =
                @Content(
                    schema = @Schema(implementation = Match.class),
                    mediaType = "application/json")),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid id supplied",
                content =
                @Content(
                    schema = @Schema(implementation = ExceptionDTO.class),
                    mediaType = "application/json")),
            @ApiResponse(
                responseCode = "404",
                description = "Match not found",
                content =
                @Content(
                    schema = @Schema(implementation = ExceptionDTO.class),
                    mediaType = "application/json")),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content)
        })
    @GetMapping("/{id}")
    public Match getMatch(@PathVariable String id) {
        return matchService.getMatch(id);
    }

    @Operation(summary = "Create a new match")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Created",
                content =
                @Content(
                    schema = @Schema(implementation = Match.class),
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
    public Match createMatch(@Valid @RequestBody MatchCreationDTO match) {
        return matchService.createMatch(match);
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
    @PostMapping("/{id}/players")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Player createPlayer(
        @PathVariable String id, @Valid @RequestBody PlayerCreationDTO player) {
        return matchService.createPlayer(player, id);
    }
}
