package com.springReactive.practice.Handler;

import com.springReactive.practice.MapService.AbstractMapService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class ReactiveHandler {

    private final AbstractMapService mapService;

    public ReactiveHandler(AbstractMapService mapService) {
        this.mapService = mapService;
    }

    public Mono<ServerResponse> getMyValue(ServerRequest request) {
        return mapService.findAll()
                .collectList()
                .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromObject(res)))
                .switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromObject("Empty body")));
    }

    public Mono<ServerResponse> getAllKeys(ServerRequest request) {
         return mapService.getAllIds()
                 .collectList()
                 .map(listofIds->listofIds.stream().map(mapService::findById).collect(Collectors.toList()))
                 .flatMap(id->ServerResponse.ok().body(BodyInserters.fromObject(id)))
                 .switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromObject("Empty body")));
    }

    public Mono<ServerResponse> getPath(ServerRequest request) {
        return ServerResponse.ok().body(BodyInserters.fromObject("hello path"));
    }

    public Mono<ServerResponse> postKeys(ServerRequest request) {
        return ServerResponse.status(HttpStatus.CREATED.value()).body(BodyInserters.fromObject("created"));
    }
}
