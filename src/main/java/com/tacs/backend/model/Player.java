package com.tacs.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A user playing on a match")
public class Player implements Serializable {
  @Schema(description = "Player Id")
  int id;

  @Schema(description = "Match in which the player will play")
  int matchId;

  @Schema(description = "User associated to the player")
  int userId;

  @Schema(description = "Is the player regular or substitute")
  boolean isRegular;
}
