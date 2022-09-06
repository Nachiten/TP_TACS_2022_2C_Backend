package com.tacs.backend.dto.creation;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreationDTO {
  @NotNull String phoneNumber;
  @NotNull String email;
}
