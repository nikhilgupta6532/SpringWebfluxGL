package com.springReactive.practice.MapService;

import java.util.List;

import com.springReactive.practice.model.Anime;
import com.springReactive.practice.model.AnimeResponse;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public interface AnimeInterface {

    Mono<AnimeResponse> postAnime(Tuple2<Anime, List<String>> animeListTuple2);
}
