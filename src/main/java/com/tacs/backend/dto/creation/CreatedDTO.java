package com.tacs.backend.dto.creation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provided when an object is created")
public class CreatedDTO {
  @Schema(description = "Created object ID")
  String id;
}
