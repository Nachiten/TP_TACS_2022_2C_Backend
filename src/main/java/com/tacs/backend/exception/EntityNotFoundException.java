package com.tacs.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotFoundException extends BackendRuntimeException {

  public EntityNotFoundException(String message, String errorCode) {
    super(message, errorCode);
  }
}
