package com.tacs.backend.exception;

import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

@NoArgsConstructor
@StandardException
public class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}

