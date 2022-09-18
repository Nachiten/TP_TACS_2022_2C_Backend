package com.tacs.backend.exception;

import com.tacs.backend.model.ErrorCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConflictException extends BackendRuntimeException {

  public ConflictException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }
}
