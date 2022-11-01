package com.tacs.backend.dto.creation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @NotEmpty
    @Schema(description = "Where the match will be played", required = true)
    String location;
}
