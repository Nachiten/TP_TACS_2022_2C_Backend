package com.tacs.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A user playing on a match")
public class Player implements Serializable {

    @Schema(description = "Date in which the player was created")
    public LocalDateTime creationDate;

    @Schema(description = "Match in which the player will play", required = true)
    String matchId;

    @Schema(description = "User phone number", required = true)
    Long phoneNumber;

    @Schema(description = "User email address", required = true)
    String email;

    @Schema(description = "Is the player regular or substitute?", required = true)
    @JsonProperty(value = "isRegular")
    boolean isRegular;

    public Player(String matchId, Long phoneNumber, String email, boolean isRegular) {
        this.matchId = matchId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isRegular = isRegular;

        this.creationDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return phoneNumber.equals(player.phoneNumber) || email.equals(player.email);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
