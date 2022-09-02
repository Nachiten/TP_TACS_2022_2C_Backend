package com.tacs.backend.service;

import com.tacs.backend.domain.Match;

import java.util.List;
import java.util.Map;

public interface IMatchService{

    void crearPartido(Match match);
    Map<String ,Match> verPartidos();

    //void AnotarsePartido();

    Match buscarUnPartido(String id);

    void borrarPartido(String id);

}
