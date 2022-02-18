package com.example.reactiveprogramming.demo;

import rx.Observable;
import rx.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class DemoPubSub {

    public static void coldPublishers() throws InterruptedException {
        // Start a cold Publisher which emits 0,1,2 every sec.
        Observable<Long> flux =  Observable.interval(1, TimeUnit.SECONDS);
        // Let's subscribe to that with multiple subscribers.
        flux.subscribe(i -> System.out.println("first_subscriber received value:" + i));
        Thread.sleep(3000);
        // Let a second subscriber come after some time 3 secs here.
        flux.subscribe(i -> System.out.println("second_subscriber received value:" + i));
    }

    public static void hotPublishers() throws InterruptedException {
        // Start a cold Publisher which emits 0,1,2 every sec.
        Observable<Long> flux =  Observable.interval(1, TimeUnit.SECONDS);
        // Make the Publisher Hot
        ConnectableObservable<Long> connectableObservable = flux.publish();
        // Now that we have a handle on the hot Publisher
        // Let's subscribe to that with multiple subscribers.
        connectableObservable.subscribe(i -> System.out.println("first_subscriber received value:" + i));
        // Start firing events with .connect() on the published flux.
        connectableObservable.connect();
        Thread.sleep(5000);
        // Let a second subscriber come after some time 3 secs here.
        connectableObservable.subscribe(i -> System.out.println("second_subscriber received value:" + i));
        Thread.sleep(5000);
    }

    public static void publishOnMainThread() {
        final Observable<String> mono = Observable.just("Hello from thread: ");
        mono.subscribe(v -> System.out.println(v + Thread.currentThread().getName()));
    }

    public static void publisherOnSeparateThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            final Observable<String> mono = Observable.just("Hello from thread: ");
            mono.subscribe(v -> System.out.println(v + Thread.currentThread().getName()));
        });
        t.start();
        t.join();
    }

    public static void main(String[] args) throws InterruptedException {
        publisherOnSeparateThread();
    }
}
