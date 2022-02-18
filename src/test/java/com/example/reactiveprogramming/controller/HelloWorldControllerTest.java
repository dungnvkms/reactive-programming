package com.example.reactiveprogramming.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest
public class HelloWorldControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void fluxTest1() {
        Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_PROBLEM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();
        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }

    @Test
    public void fluxTest2() {
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_PROBLEM_JSON)
                .exchange()
                .expectHeader()
                .contentType(MediaType.TEXT_EVENT_STREAM_VALUE)
                .expectBodyList(Integer.class)
                .hasSize(4);
    }
}
