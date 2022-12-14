package com.tacs.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacs.backend.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A user playing on a match")
public class Player implements Serializable {

    @Schema(description = "Date in which the player was created")
    LocalDateTime creationDate;

    @Schema(description = "Match in which the player will play", required = true)
    String matchId;

    @Schema(description = "User phone number", required = true)
    Long phoneNumber;

    @Schema(description = "User email address", required = true)
    String email;

    @Schema(description = "Is the player regular or substitute?", required = true)
    @JsonProperty(value = "isRegular")
    Boolean isRegular;

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

    @Override
    public String toString() {
        return
            "    - Creation Date: " + DateUtils.localDateTimeToString(creationDate) + "\n" +
            "    - Phone Number: " + phoneNumber + "\n" +
            "    - Email: " + email + "\n" +
            "    - Is Regular?: " + isRegular + "\n\n";
    }
}
