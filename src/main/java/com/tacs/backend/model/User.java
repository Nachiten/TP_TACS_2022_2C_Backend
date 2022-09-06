package com.tacs.backend.model;

import com.redis.om.spring.annotations.Document;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "A User of the system")
@Document
public class User implements Serializable {
  @Id
  @Schema(description = "User ID")
  String id;

  @Schema(description = "User phone number", required = true)
  String phoneNumber;

  @Schema(description = "User email address", required = true)
  String email;

  @Schema(description = "Matches which the user has joined")
  List<Player> matchesJoined;

  public User(String phoneNumber, String email) {
    this.phoneNumber = phoneNumber;
    this.email = email;
  }
}
