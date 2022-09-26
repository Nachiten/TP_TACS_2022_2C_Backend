package com.tacs.backend.dto;

import com.tacs.backend.model.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
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

  public ExceptionDTO(String exceptionName, String message, ErrorCode errorCode) {
    this.exceptionName = exceptionName;
    this.message = message;
    this.errorCode = errorCode.name();
  }
}
