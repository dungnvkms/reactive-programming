package com.example.reactiveprogramming.demo;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class DemoPubSub {

    public static void coldPublishers() throws InterruptedException {
        // Start a cold Publisher which emits 0,1,2 every sec.
        Flux<Long> flux =  Flux.interval(Duration.ofSeconds(1));
        // Let's subscribe to that with multiple subscribers.
        flux.subscribe(i -> System.out.println("first_subscriber received value:" + i));
        Thread.sleep(3000);
        // Let a second subscriber come after some time 3 secs here.
        flux.subscribe(i -> System.out.println("second_subscriber received value:" + i));
    }

    public static void hotPublishers() throws InterruptedException {
        // Start a cold Publisher which emits 0,1,2 every sec.
        Flux<Long> flux =  Flux.interval(Duration.ofSeconds(1));
        // Make the Publisher Hot
        ConnectableFlux<Long> connectableFlux = flux.publish();
        // Now that we have a handle on the hot Publisher
        // Let's subscribe to that with multiple subscribers.
        connectableFlux.subscribe(i -> System.out.println("first_subscriber received value:" + i));
        // Start firing events with .connect() on the published flux.
        connectableFlux.connect();
        Thread.sleep(5000);
        // Let a second subscriber come after some time 3 secs here.
        connectableFlux.subscribe(i -> System.out.println("second_subscriber received value:" + i));
        Thread.sleep(5000);
    }

    public static void publishOnMainThread() {
        final Mono<String> mono = Mono.just("Hello from thread: ");
        mono.subscribe(v -> System.out.println(v + Thread.currentThread().getName()));
    }

    public static void publisherOnSeparateThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            final Mono<String> mono = Mono.just("Hello from thread: ");
            mono.subscribe(v -> System.out.println(v + Thread.currentThread().getName()));
        });
        t.start();
        t.join();
    }

    public static void main(String[] args) throws InterruptedException {
        publisherOnSeparateThread();
    }
}
