package com.example.reactiveprogramming.demo;

import reactor.core.publisher.Flux;

public class Demo4FluxCreation {

    public static Flux<String> fluxWithGenerate() {
        return Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3*state);
                    if (state == 10) sink.complete();
                    return state + 1;
                });
    }

    public static void main(String[] args) {
        fluxWithGenerate().subscribe(System.out::println);
    }
}
