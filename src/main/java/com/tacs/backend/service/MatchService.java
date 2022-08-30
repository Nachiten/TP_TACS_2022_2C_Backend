package com.tacs.backend.service;

import com.tacs.backend.model.Match;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
  public Match createMatch(Match match) {
    System.out.println(
        "Creating match with DATE: "
            + match.getDate()
            + " | TIME: "
            + match.getTime()
            + " | LOCATION: "
            + match.getLocation());

    match.setId(25);

    return match;
  }
}
