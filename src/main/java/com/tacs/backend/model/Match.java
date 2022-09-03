package com.tacs.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A football match")
public class Match implements Serializable {
  @Schema(description = "Match ID")
  int id;

  @Schema(description = "Date in which the match was created")
  LocalDateTime creationDate;

  @NotNull
  @Schema(description = "Date in which the match will occur", required = true)
  LocalDate startingDate;

  @NotNull
  @Schema(description = "Hour in which the match will occur", required = true)
  LocalTime startingTime;

  @NotNull
  @Schema(description = "Where the match will be played", required = true)
  String location;

  @Schema(description = "List of players of the match")
  List<Player> players;

  public Match() {
    this.players = new ArrayList<>();
    this.creationDate = LocalDateTime.now();
  }
}
