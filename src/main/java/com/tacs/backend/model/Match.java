package com.tacs.backend.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Match {
  int id;
  Date date;
  Time time;
  String location;
  List<User> regularPlayers;
  List<User> substitutePlayers;
}
