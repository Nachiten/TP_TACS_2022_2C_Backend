package com.tacs.backend.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {
  int id;
  int phoneNumber;
  String email;
  List<Player> matchesJonined;
}
