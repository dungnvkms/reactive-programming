package com.example.reactiveprogramming.demo;

import reactor.core.publisher.Mono;

public class Demo1Async {
    // every unit is a node
    public static void main(String[] args) {
        Mono<String> baker = Mono.just("I am a Baker");
        Mono<String> waiter = Mono.just("I am a Waiter");
        Mono<String> client = Mono.just("I am a Client");
    }
}
