package com.example.reactiveprogramming.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController
public class HelloWorldController {

    @GetMapping("/blocking")
    public List<Integer> getBlockingData() {
        List<Integer> data = IntStream.range(1, 10)
                // mimic a DB call time-consuming task
                .map(i -> {
                    sleep(1000);
                    return i;
                })
                // mimic an external call time-consuming task
                .map(i -> {
                    sleep(1000);
                    return i;
                })
                .boxed().collect(Collectors.toList());
        return data;
    }

    @GetMapping(value = "/non-blocking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getNonBlockingData() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping("/flux")
    public Flux<Integer> getFlux() {
        return Flux.just(1, 2, 3, 4)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/fluxStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getFluxStream() {
        return Flux.just(1, 2, 3, 4)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/fluxInfiniteStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> getInfiniteStream() {
        // khi server stop và ngược lại, hắn sẽ gửi 1 signal cancel trong subscription tới client, và client nhận được tín hiệu,
        // sẽ xử lý
        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping("/mono")
    public Mono<Integer> getMono() {
        return Mono.just(1)
                .log();
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
