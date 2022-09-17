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
@Schema(description = "System exception")
public class ExceptionDTO {
  @Schema(description = "Exception name")
  private String exceptionName;

  @Schema(description = "Exception message")
  private String message;

  @Schema(description = "Exception error code")
  private String errorCode;
}
