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
  @Schema(description = "Phone number of the user who is playing", required = true)
  Long phoneNumber;

  @NotNull
  @Schema(description = "Email of the user who is playing", required = true)
  String email;
}
