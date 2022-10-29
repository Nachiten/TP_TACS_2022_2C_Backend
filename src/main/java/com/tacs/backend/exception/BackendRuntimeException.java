package com.tacs.backend.exception;

import com.tacs.backend.model.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BackendRuntimeException extends RuntimeException {
    ErrorCode errorCode;

    public BackendRuntimeException(String message, ErrorCode errorCode) {
        super(message);

        this.errorCode = errorCode;
    }
}
