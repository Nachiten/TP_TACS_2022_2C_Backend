package com.tacs.backend.model;

import com.redis.om.spring.annotations.Document;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class User implements Serializable {
  @Id String id;
  int phoneNumber;
  String email;
  List<Player> matchesJonined;
}
