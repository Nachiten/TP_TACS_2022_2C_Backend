package com.tacs.backend.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BackendRuntimeException extends RuntimeException {
  String errorCode;

  public BackendRuntimeException(String message, String errorCode) {
    super(message);

    this.errorCode = errorCode;
  }
}
