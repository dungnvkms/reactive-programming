package com.example.reactiveprogramming.demo;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class Sample {
    public static void main(String[] args) {
        List<String> symbols = Arrays.asList("GOOG", "AAPL", "MSFT", "INTC");

        Flux<String> feed = Flux.just("GOOG", "AAPL", "MSFT", "INTC");
        feed.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> {
                    System.out.println("Done");
                },
                s -> s.request(2));

        feed.subscribe(System.out::println);
    }
}
