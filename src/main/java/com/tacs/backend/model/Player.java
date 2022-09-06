package com.tacs.backend.model;

import com.redis.om.spring.annotations.Document;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A user playing on a match")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
public class Player implements Serializable {
  @Id
  @Schema(description = "Player Id")
  String id;

  @Schema(description = "Date in which the player was created")
  public LocalDateTime creationDate;

  @Schema(description = "Match in which the player will play", required = true)
  String matchId;

  @Schema(description = "User who is playing", required = true)
  String userId;

  @Schema(description = "Is the player regular or substitute?", required = true)
  boolean isRegular;

  public Player(String matchId, String userId, boolean isRegular) {
    this.matchId = matchId;
    this.userId = userId;
    this.isRegular = isRegular;

    this.creationDate = LocalDateTime.now();
  }
}
