package com.tacs.backend.model;

import com.redis.om.spring.annotations.Document;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A user playing on a match")
@Document
public class Player implements Serializable {
  @Schema(description = "Player Id")
  int id;

  @Schema(description = "Match in which the player will play", required = true)
  @NotNull
  String matchId;

  @Schema(description = "User associated to the player", required = true)
  String userId;

  @Schema(description = "Is the player regular or substitute", required = true)
  boolean isRegular;
}
