package com.tacs.backend.model;

import com.redis.om.spring.annotations.Document;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A football match")
@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Match implements Serializable {
  @Id
  @Schema(description = "Match ID")
  String id;

  @Schema(description = "Date in which the match was created")
  public LocalDateTime creationDate;

  @Schema(description = "Date in which the match will occur", required = true)
  public LocalDate startingDate;

  @Schema(description = "Hour in which the match will occur", required = true)
  public LocalDateTime startingTime;

  @Schema(description = "Where the match will be played", required = true)
  String location;

  @Schema(description = "List of players of the match")
  List<Player> players;

  public Match(String location, LocalDate startingDate, LocalDateTime startingTime) {
    this.location = location;
    this.startingDate = startingDate;
    this.startingTime = startingTime;

    this.creationDate = LocalDateTime.now();
    this.players = new ArrayList<>();
  }
}
