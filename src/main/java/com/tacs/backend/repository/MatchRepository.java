package com.tacs.backend.repository;

import com.tacs.backend.model.Match;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {
    Iterable<Match> findAllByCreationDateGreaterThan(LocalDateTime fromDate);

    @Aggregation(pipeline = {"{ $unwind: '$players' }", "{ $match: { creationDate: { $gte: ?0 } } }", "{ $group: { _id: null , count: { $sum: 1 } } }"})
    Long countAllPlayersInAllMatchesDateGreaterThan(LocalDateTime fromDate);
}
