package com.tacs.backend.dto.creation;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "DTO for creating a match")
public class MatchCreationDTO {
  @NotNull
  @Schema(description = "Date in which the match will occur", required = true)
  public LocalDateTime startingDateTime;

  @NotNull
  @Schema(description = "Where the match will be played", required = true)
  String location;
}
