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
@Schema(description = "DTO for creating an User")
public class UserCreationDTO {
  @NotNull
  @Schema(description = "User phone number", required = true)
  String phoneNumber;

  @NotNull
  @Schema(description = "User email address", required = true)
  String email;
}
