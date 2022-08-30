package com.tacs.backend.controller;

import com.tacs.backend.model.Match;
import com.tacs.backend.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
public class MatchController {
  @Autowired private MatchService matchService;

  @PostMapping()
  public Match createMatch(@RequestBody Match match) {
    return matchService.createMatch(match);
  }
}
