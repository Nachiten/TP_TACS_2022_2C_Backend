package com.tacs.backend.repository;

import com.tacs.backend.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {

    Iterable<Match> findAllByCreationDateGreaterThan(LocalDateTime fromDate);
}
