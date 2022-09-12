package com.tacs.backend.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "A User of the system")
@Document
public class User implements Serializable {
  @Id
  @Schema(description = "User ID")
  String id;

  @Schema(description = "User phone number", required = true)
  String phoneNumber;

  @Schema(description = "User email address", required = true)
  @Indexed
  String email;

  @Schema(description = "Matches which the user has joined")
  List<Player> matchesJoined;

  public User(String phoneNumber, String email) {
    this.phoneNumber = phoneNumber;
    this.email = email;
  }
}
