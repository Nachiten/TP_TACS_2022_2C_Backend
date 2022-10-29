package com.tacs.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A football match")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "matches")
public class Match implements Serializable {
    @Id
    @Schema(description = "Match ID")
    private String id;

    @Schema(description = "Date in which the match was created")
    private LocalDateTime creationDate;

    @Schema(description = "Date in which the match will occur", required = true)
    @EqualsAndHashCode.Include
    private LocalDateTime startingDateTime;

    @Schema(description = "Where the match will be played", required = true)
    @EqualsAndHashCode.Include
    private String location;

    @Schema(description = "List of players of the match")
    private List<Player> players;

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }

    public Match(String location, LocalDateTime startingDateTime) {
        this.location = location;
        this.startingDateTime = startingDateTime;

        this.creationDate = LocalDateTime.now();
        this.players = new ArrayList<>();
    }
}
