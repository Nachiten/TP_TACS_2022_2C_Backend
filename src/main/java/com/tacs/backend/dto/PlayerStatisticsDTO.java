package com.tacs.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Players statistics")
public class PlayerStatisticsDTO {
    @Schema(description = "Number of players enrolled in the last two hours")
    Long playersEnrolled;

    LocalDateTime now;
}
