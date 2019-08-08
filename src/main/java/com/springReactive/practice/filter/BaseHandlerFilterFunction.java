package com.springReactive.practice.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BaseHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        Mono<ServerResponse> response;
        if (!isAcceptHeaderJson(request)) {
            response = ServerResponse.status(HttpStatus.NOT_ACCEPTABLE.value()).build();
        } else if (isMethod(request, HttpMethod.POST.toString()) && !isContentTypeHeader(request)) {
            response = ServerResponse.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).build();
        } else {
            return next.handle(request);
        }
        return response;
    }

    private boolean isAcceptHeaderJson(ServerRequest request) {
        return ((request.headers().header(HttpHeaders.ACCEPT).contains(MediaType.APPLICATION_JSON_VALUE)) || (request.headers().header(HttpHeaders.ACCEPT).contains(MediaType.APPLICATION_STREAM_JSON_VALUE)));
    }

    private boolean isContentTypeHeader(ServerRequest request) {
        return (request.headers().header(HttpHeaders.CONTENT_TYPE).contains(MediaType.APPLICATION_JSON_VALUE));
    }

    private boolean isMethod(ServerRequest request, String methodName) {
        return (request.methodName().equals(methodName));
    }
}
