package com.tacs.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Matches statistics")
public class StatisticsDTO {
  @Schema(description = "Number of games created in the last two hours")
  int gamesCreated;
}
