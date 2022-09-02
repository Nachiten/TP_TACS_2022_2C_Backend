package com.tacs.backend.controller;
import com.tacs.backend.domain.Match;
import com.tacs.backend.dto.TestDTO;
import com.tacs.backend.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class MatchController {


    @Autowired
    IMatchService matchService;

    //public MatchController(MatchRepository matchRepository){
    //    this.matchRepository = matchRepository;
    //}
    @GetMapping("/health")
    public TestDTO health() {
        return new TestDTO("Healthy!");
    }

    @GetMapping("/matches")
    public Map<String ,Match> matchgridview(){
        return matchService.verPartidos();

    }

    @GetMapping("/matches/{id}")
    public Match buscarUnPartido(@PathVariable String id){
        return matchService.buscarUnPartido(id);
    }


    @PostMapping(value = "/matches")
    public void createMatch(@RequestBody Match match){
        matchService.crearPartido(match);

    }

    @DeleteMapping("/matches/{id}")
    public void borrarPartido(@PathVariable String id){
        matchService.borrarPartido(id);
    }



}
