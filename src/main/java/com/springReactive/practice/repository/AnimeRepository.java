package com.springReactive.practice.repository;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.springReactive.practice.model.AnimeModel;

import reactor.core.publisher.Mono;

@Repository
@N1qlPrimaryIndexed
public interface AnimeRepository extends ReactiveCouchbaseRepository<AnimeModel, String> {

  @Query("SELECT Exists(SELECT * from nikhil where name = $1)")
  Mono<Boolean> existsByAnimeName(String name);

  Mono<AnimeModel> findByName(String animeName);
}
