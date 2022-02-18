package com.example.reactiveprogramming.demo;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Demo2Backpressure {
    // batching emitted items
    // skipping elements

    // subscriber side: request, cancel.
    public static void demoBackpressureBuffer() {
        Flux<Long> x = Flux.interval(Duration.ofSeconds(1));
        x.onBackpressureBuffer().subscribe(System.out::println);
    }

    public static void demoBackpressureDrop() {
        Flux<Long> x = Flux.interval(Duration.ofSeconds(1));
        x.onBackpressureDrop().subscribe(System.out::println);
    }
    public static void main(String[] args) {
        Flux<String> x = Flux.just("a", "b", "c");
        x.onBackpressureError();
        //
        x.onBackpressureLatest();
    }
}
