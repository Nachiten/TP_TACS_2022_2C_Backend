package com.tacs.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConflictException extends BackendRuntimeException {

  public ConflictException(String message, String errorCode) {
    super(message, errorCode);
  }
}
