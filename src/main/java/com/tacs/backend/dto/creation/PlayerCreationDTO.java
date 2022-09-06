package com.tacs.backend.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerCreationDTO {
    @NotNull
    String matchId;
    @NotNull
    String userPhoneNumber;
    @NotNull
    String userEmail;
}
