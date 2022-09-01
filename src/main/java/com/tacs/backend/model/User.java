package com.tacs.backend.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  int id;
  int phoneNumber;
  String email;
  List<Match> matchesJoined;
}
