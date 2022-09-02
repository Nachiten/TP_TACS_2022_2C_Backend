package com.tacs.backend.service;

import com.tacs.backend.domain.Match;
import com.tacs.backend.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class MatchServiceImpl implements IMatchService{

    @Autowired
    private MatchRepository matchRepository;


    @Override
    public void crearPartido(Match match) {
        matchRepository.save(match);
    }

    @Override
    public Map<String ,Match> verPartidos() {
        return matchRepository.findAll();
    }

    //@Override
    //public void AnotarsePartido() {
     // matchRepository.save();
   // }

    @Override
    public Match buscarUnPartido(String id) {
        return matchRepository.findById(id);
    }

    @Override
    public void borrarPartido(String id) {
        matchRepository.delete(id);
    }


}
