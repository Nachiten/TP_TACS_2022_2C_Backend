package com.tacs.backend;

import com.tacs.backend.dto.CreationDTO;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class BackendApplicationTests {
    @Autowired
    MatchService matchService;


    @Test
    public void testCreateMatch() throws Exception{


        Player[] unJugador = {new Player(1,1,1,true)};
        List<Player> listaJugadores = Arrays.asList(unJugador);

        Match unMatch = new Match();

        unMatch.setCreationDate(LocalDateTime.now());
        unMatch.setStartingDate(LocalDate.now());
        unMatch.setLocation("Best field");
        unMatch.setPlayers(listaJugadores);



        // agrego un partido
        String id = matchService.createMatch(unMatch);
        // obtengo ese partido y comparo si es igual

        Match result = matchService.getMatch(id);


        assertEquals(id, result.getId());

        //assertThat(result.getId()).isEqualTo(id);

        // se borra el documento cargado

        matchService.deleteMatch(id);

    }

    @Test
     public void testGetMatches() throws Exception{


        Player[] unJugador = {new Player(1,1,1,true)};
        List<Player> listaJugadores = Arrays.asList(unJugador);

        Match match1 = new Match();

        match1.setCreationDate(LocalDateTime.now());
        match1.setStartingDate(LocalDate.now());
        match1.setLocation("Best field1");
        match1.setPlayers(listaJugadores);

        Match match2 = new Match();

        match2.setCreationDate(LocalDateTime.now());
        match2.setStartingDate(LocalDate.now());
        match2.setLocation("Best field2");
        match2.setPlayers(listaJugadores);

        // se agregan dos partidos
        List<String> listMatches= List.of(matchService.createMatch(match1),
                                            matchService.createMatch(match2));


        assertEquals(2,listMatches.size());

        for(String id : listMatches){
            matchService.deleteMatch(id);

        }


    }








}
