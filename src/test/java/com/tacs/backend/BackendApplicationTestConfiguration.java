package com.tacs.backend;

import com.tacs.backend.model.Match;
import com.tacs.backend.repository.MatchRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Profile("BackendTests")
@Configuration
public class BackendApplicationTestConfiguration {
    private final ArrayList<Match> matches = new ArrayList<>();
    private int lastId = 1;

    @Bean
    @Primary
    public MatchRepository mockMatchRepository() {
        MatchRepository matchRepository = Mockito.mock(MatchRepository.class);

        Mockito.when(matchRepository.findAll()).thenReturn(matches);
        Mockito.when(matchRepository.save(Mockito.any(Match.class))).thenAnswer(i -> {
            Match match = i.getArgument(0);

            // See if there is already a match with id
            Optional<Match> existingMatch = getMatchById(match.getId());

            if (existingMatch.isPresent()) {
                // If there is, replace it
                matches.set(matches.indexOf(existingMatch.get()), match);
                System.out.println("EXISTING Match saved with ID: " + match.getId());
            } else {
                // If not, add it
                match.setId(String.valueOf(lastId++));
                matches.add(match);

                System.out.println("NEW Match saved with ID: " + match.getId());
            }

            return match;
        });
        Mockito.when(matchRepository.findById(Mockito.anyString())).thenAnswer(i -> {
            String id = i.getArgument(0);
            System.out.println("Match searched with ID: " + id);
            return getMatchById(id);
        });

        Mockito.when(matchRepository.countAllMatchesCreatedDateGreaterThan(Mockito.any(LocalDateTime.class))).thenAnswer(i -> {
            LocalDateTime fromDate = i.getArgument(0);
            System.out.println("Matches searched with creation date greater than: " + fromDate);
            int arraySize = matches.stream().filter(m -> m.getCreationDate().isAfter(fromDate)).toList().size();

            // Return arraySize as Long
            return (long) arraySize;
        });

        Mockito.when(matchRepository.countAllPlayersInAllMatchesDateGreaterThan(Mockito.any(LocalDateTime.class))).thenAnswer(i -> {
            LocalDateTime fromDate = i.getArgument(0);

            long players = matches.stream()
                .flatMap(match -> match.getPlayers().stream())
                .distinct()
                .filter(player -> player.getCreationDate().isAfter(fromDate))
                .count();

            System.out.println("Players searched with creation date greater than: " + fromDate);
            return players;
        });

        return matchRepository;
    }

    private Optional<Match> getMatchById(String id) {
        return matches.stream().filter(m -> m.getId().equals(id)).findFirst();
    }
}
