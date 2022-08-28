package com.tacs.backend.controller;

import com.tacs.backend.dto.TestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
  @GetMapping("/health")
  public TestDTO health() {
    return new TestDTO("Healthy!");
  }
}
