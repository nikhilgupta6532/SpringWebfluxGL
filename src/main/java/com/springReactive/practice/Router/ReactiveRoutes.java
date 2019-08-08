package com.springReactive.practice.Router;

import com.springReactive.practice.Handler.ReactiveHandler;
import com.springReactive.practice.filter.BaseHandlerFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ReactiveRoutes {

    @Bean
    public RouterFunction<ServerResponse> myRoute(ReactiveHandler reactiveHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/getMe"),reactiveHandler::getMyValue)
                .andRoute(RequestPredicates.GET("/getAllKeys"),reactiveHandler::getAllKeys)
                .andRoute(RequestPredicates.POST("/postKeys"),reactiveHandler::postKeys)
                .andRoute(RequestPredicates.GET("/debugStream"),reactiveHandler::debugStream)
                .andRoute(RequestPredicates.POST("/insertAnimes"),reactiveHandler::insertAnimes)
                .filter(new BaseHandlerFilterFunction());
    }
}
