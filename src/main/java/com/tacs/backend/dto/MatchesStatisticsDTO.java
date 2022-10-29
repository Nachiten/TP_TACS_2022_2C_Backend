package com.tacs.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Matches statistics")
public class MatchesStatisticsDTO {
    @Schema(description = "Number of matches created in the last two hours")
    long matchesCreated;

    LocalDateTime now;
}
