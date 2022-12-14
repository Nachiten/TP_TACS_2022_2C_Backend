package com.tacs.backend.exception;

import com.tacs.backend.dto.ExceptionDTO;
import com.tacs.backend.model.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestController
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    // --- CUSTOM exceptions --

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ExceptionDTO> handleEntityNotFoundException(
        EntityNotFoundException ex) {
        HttpStatus statusCode = HttpStatus.NOT_FOUND;

        return generateResponseEntity(ex, statusCode, "EntityNotFoundException");
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ExceptionDTO> handleConflictException(ConflictException ex) {
        HttpStatus statusCode = HttpStatus.CONFLICT;

        return generateResponseEntity(ex, statusCode, "ConflictException");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException ex) {

        HttpStatus statusCode = HttpStatus.BAD_REQUEST;

        String errors = getErrors(ex);

        ExceptionDTO exceptionDTO =
            new ExceptionDTO(
                "MethodArgumentTypeMismatchException", errors, ErrorCode.INVALID_QUERY_PARAM);
        return new ResponseEntity<>(exceptionDTO, statusCode);
    }

    // --- SPRING exceptions ---

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {

        HttpStatus statusCode = HttpStatus.BAD_REQUEST;

        String errors = getErrors(ex);

        return handleExceptionInternal(
            ex,
            new ExceptionDTO("MethodArgumentNotValidException", errors, ErrorCode.INVALID_BODY),
            headers,
            statusCode,
            request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {

        HttpStatus statusCode = HttpStatus.BAD_REQUEST;

        String errors = getErrors(ex);

        return handleExceptionInternal(
            ex,
            new ExceptionDTO(
                "MissingServletRequestParameterException", errors, ErrorCode.MISSING_QUERY_PARAM),
            headers,
            statusCode,
            request);
    }

    // --- UTILS ---

    private String getErrors(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : exception.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        // Make a string containing all the errors
        StringBuilder errorsString = new StringBuilder();

        for (String error : errors) {
            if (error.equals(errors.get(errors.size() - 1))) {
                errorsString.append(error);
            } else {
                errorsString.append(error).append(", ");
            }
        }

        return errorsString.toString();
    }

    private String getErrors(MissingServletRequestParameterException ex) {
        return "'" + ex.getParameterName() + "' parameter is missing of type " + ex.getParameterType();
    }

    private String getErrors(MethodArgumentTypeMismatchException ex) {
        return "'" + ex.getName() + "' should be of type " + ex.getRequiredType();
    }

    private ResponseEntity<ExceptionDTO> generateResponseEntity(
        BackendRuntimeException ex, HttpStatus statusCode, String exceptionName) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exceptionName, ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(exceptionDTO, statusCode);
    }
}
