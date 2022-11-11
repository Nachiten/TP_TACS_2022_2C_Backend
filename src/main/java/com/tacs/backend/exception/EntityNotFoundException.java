package com.tacs.backend.exception;

import com.tacs.backend.model.ErrorCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotFoundException extends BackendRuntimeException {

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
