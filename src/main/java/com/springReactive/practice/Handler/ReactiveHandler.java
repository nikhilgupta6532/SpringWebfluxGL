package com.springReactive.practice.Handler;

import com.springReactive.practice.MapService.AbstractMapService;
import com.springReactive.practice.MapService.AnimeInterface;
import com.springReactive.practice.helper.ModelValidator;
import com.springReactive.practice.model.Anime;
import com.springReactive.practice.model.AnimeResponse;
import com.springReactive.practice.model.ValidationResult;
import com.springReactive.practice.utils.PlatformErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ReactiveHandler {

  @Autowired
  private AnimeInterface animeInterface;

  @Autowired
  private ModelValidator validator;

  private final AbstractMapService mapService;

  public ReactiveHandler(AbstractMapService mapService) {
    this.mapService = mapService;
  }

  public Mono<ServerResponse> getMyValue(ServerRequest request) {
    return mapService.findAll().collectList()
        .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromObject(res)))
        .switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromObject("Empty body")));
  }

  public Mono<ServerResponse> getAllKeys(ServerRequest request) {
    return mapService.getAllIds().collectList()
        .map(listofIds -> listofIds.stream().map(mapService::findById).collect(Collectors.toList()))
        .flatMap(id -> ServerResponse.ok().body(BodyInserters.fromObject(id)))
        .switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromObject("Empty body")));
  }

  public Mono<ServerResponse> getPath(ServerRequest request) {
    return ServerResponse.ok().body(BodyInserters.fromObject("hello path"));
  }

  public Mono<ServerResponse> postKeys(ServerRequest request) {
    return ServerResponse.status(HttpStatus.CREATED.value())
        .body(BodyInserters.fromObject("created"));
  }

  public Mono<ServerResponse> debugStream(ServerRequest request) {
    Stream.of("hello", "hello", "nikhil", "gupta").filter(text -> text.startsWith("h")).distinct()
        .collect(Collectors.toList());

    return ServerResponse.ok().build();
  }

  public Mono<ServerResponse> insertAnimes(ServerRequest request) {
    Flux<Anime> listOfAnimes = request.bodyToFlux(Anime.class);

    Flux<AnimeResponse> animeResponseFlux = listOfAnimes
        .flatMap(anime -> validator.applyBeanValidation(anime, Default.class))
        .flatMap(tuple -> animeInterface.postAnime(tuple));

    return ServerResponse.status(HttpStatus.MULTI_STATUS)
        .body(BodyInserters.fromPublisher(animeResponseFlux, AnimeResponse.class));
  }
}
