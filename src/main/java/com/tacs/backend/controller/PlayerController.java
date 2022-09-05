package com.tacs.backend.controller;

import com.tacs.backend.dto.ExceptionDTO;
import com.tacs.backend.model.Player;
import com.tacs.backend.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class PlayerController {
  @Autowired PlayerService playerService;

    @Operation(summary = "Create a new player")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid body",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json")),
                    @ApiResponse(
                            responseCode = "405",
                            description = "Method-Not-Allow",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content)
            })
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createMatch(@Valid @RequestBody Player player) {
        playerService.createPlayer(player);
    }

}
