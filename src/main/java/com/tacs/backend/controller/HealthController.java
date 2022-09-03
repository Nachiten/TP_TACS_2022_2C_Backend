package com.tacs.backend.controller;

import com.tacs.backend.dto.HealthDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

  @Operation(hidden = true)
  @GetMapping()
  public HealthDTO health() {
    return new HealthDTO("Healthy!");
  }
}
