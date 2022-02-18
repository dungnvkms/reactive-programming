package com.example.reactiveprogramming.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Demo3ErrorHandling {

    public static void exceptionExample() {
        Flux<String> people = Flux.just("A", "B", "C")
                .concatWith(Mono.error(new RuntimeException("this is an error")))
                .concatWith(Flux.just("D", "E", "F"));
        people.subscribe(System.out::println);
    }

    public static void exceptionDownStreamHandling() {
        Flux<String> people = Flux.just("A", "B", "C")
                .concatWith(Mono.error(new RuntimeException("this is an error")))
                .concatWith(Flux.just("D", "E", "F"));
        people.subscribe(System.out::println, throwable -> System.out.println(throwable.getMessage()));
    }

    public static void exceptionHandleError() {
        Flux.just("A", "B", "C")
                .concatWith(Mono.error(new RuntimeException("this is an error")))
                .onErrorReturn("new value")
                .subscribe(System.out::println);
    }

    public static void exceptionCallABackup() {
        Flux<String> people = Flux.just("A", "B", "C")
                .concatWith(Mono.error(new RuntimeException("this is an error")))
                .concatWith(Flux.just("D", "E", "F"));
        people.onErrorResume(throwable -> {
                    System.out.println("switch to backup server");
                    return Flux.just("a", "b", "c");
                })
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        exceptionDownStreamHandling();
    }
}
