package com.tacs.backend.dto;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiErrorDTO {
  private HttpStatus status;
  private List<String> errors;
  private String message;

  public ApiErrorDTO(HttpStatus status, String message, List<String> errors) {
    super();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  public ApiErrorDTO(HttpStatus status, String message, String error) {
    super();
    this.status = status;
    this.message = message;
    errors = Arrays.asList(error);
  }
}
