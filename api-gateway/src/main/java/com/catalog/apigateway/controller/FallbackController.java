package com.catalog.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/user-service")
    public Mono<ResponseEntity<String>> userServiceFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Lo User Service non è disponibile al momento. Riprova più tardi."));
    }

    @RequestMapping("/fallback/calendar-service")
    public Mono<ResponseEntity<String>> calendarServiceFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Il Calendar Service non è disponibile al momento. Riprova più tardi."));
    }
}