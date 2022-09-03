package com.tacs.backend.controller;

import com.tacs.backend.domain.Match;
import com.tacs.backend.service.IMatchService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchController {
  @Autowired IMatchService matchService;

  // public MatchController(MatchRepository matchRepository){
  //    this.matchRepository = matchRepository;
  // }

  @GetMapping("/buscar")
  public Map<String, Match> matchgridview() {
    return matchService.verPartidos();
  }

  @GetMapping("/{id}")
  public Match buscarUnPartido(@PathVariable String id) {
    return matchService.buscarUnPartido(id);
  }

  @PostMapping()
  public void createMatch(@RequestBody Match match) {
    matchService.crearPartido(match);
  }

  @DeleteMapping("/{id}")
  public void borrarPartido(@PathVariable String id) {
    matchService.borrarPartido(id);
  }
}
