package com.tacs.backend.model;

import com.redis.om.spring.annotations.Document;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Schema(description = "A football match")
@Document
public class Match implements Serializable {
  @Schema(description = "Match ID")
  @Id
  String id;

  @Schema(description = "Date in which the match was created")
  public LocalDateTime creationDate;

  @NotNull
  @Schema(description = "Date in which the match will occur", required = true)
  public LocalDate startingDate;

  @NotNull
  @Schema(description = "Hour in which the match will occur", required = true)
  public LocalDateTime startingTime;

  @NotNull
  @Schema(description = "Where the match will be played", required = true)
  String location;

  @Schema(description = "List of players of the match")
  List<Player> players;

  public Match() {
    this.players = new ArrayList<>();
    this.creationDate = LocalDateTime.now();
  }

  public Match(String location, LocalDate startingDate, LocalDateTime startingTime) {
    this.players = new ArrayList<>();
    this.creationDate = LocalDateTime.now();
    this.location = location;
    this.startingDate = startingDate;
    this.startingTime = startingTime;
  }
}
