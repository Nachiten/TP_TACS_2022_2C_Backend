package com.tacs.backend.exception;

import com.tacs.backend.dto.ApiErrorDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    HttpStatus statusCode = HttpStatus.BAD_REQUEST;

    List<String> errors = getErrors(exception);
    ApiErrorDTO apiError = new ApiErrorDTO(statusCode, exception.getMessage(), errors);

    return handleExceptionInternal(exception, apiError, headers, apiError.getStatus(), request);
  }

  //  @ExceptionHandler(EntityNotFoundException.class)
  //  protected ResponseEntity<Object> handleEntityNotFound(
  //          EntityNotFoundException exception,
  //          HttpHeaders headers,
  //          HttpStatus status,
  //          WebRequest request) {
  //
  //    HttpStatus statusCode = HttpStatus.NOT_FOUND;
  //
  //    List<String> errors = new ArrayList<>();
  //    ApiErrorDTO apiError = new ApiErrorDTO(statusCode, exception.getMessage(), errors);
  //
  //    return new ResponseEntity<>(apiError, statusCode);
  //  }

  List<String> getErrors(MethodArgumentNotValidException exception) {
    List<String> errors = new ArrayList<String>();
    for (FieldError error : exception.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (ObjectError error : exception.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    return errors;
  }

  protected ResponseEntity<Object> handleNotFound(
      NotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ApiErrorDTO apiError =
        new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), new ArrayList<>());
    return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
  }
}
