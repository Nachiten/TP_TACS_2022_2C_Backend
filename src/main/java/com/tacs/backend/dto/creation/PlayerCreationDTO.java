package com.tacs.backend.dto.creation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "DTO for creating a player")
public class PlayerCreationDTO {
    @NotNull
    @Min(1000)
    @Max(99999999999L)
    @Schema(description = "Phone number of the user who is playing", required = true)
    Long phoneNumber;

    @NotNull
    @NotEmpty
    @Email(regexp = ".+@.+[\\.].+")
    @Schema(description = "Email of the user who is playing", required = true)
    String email;
}
