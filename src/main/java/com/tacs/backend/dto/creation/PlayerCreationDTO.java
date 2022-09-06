package com.tacs.backend.dto.creation;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "DTO for creating a player")
public class PlayerCreationDTO {
  @NotNull
  @Schema(description = "Match in which the player will play", required = true)
  String matchId;

  @NotNull
  @Schema(description = "Phone number of the user who is playing", required = true)
  String userPhoneNumber;

  @NotNull
  @Schema(description = "Email of the user who is playing", required = true)
  String userEmail;
}
