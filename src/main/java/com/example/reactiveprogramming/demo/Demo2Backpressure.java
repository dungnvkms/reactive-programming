package com.example.reactiveprogramming.demo;


import rx.Observable;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Demo2Backpressure {
    // batching emitted items
    // skipping elements

    // subscriber side: request, cancel.
    public static void demoBackpressureBuffer() {
        Observable<Long> x = Observable.interval(1, TimeUnit.SECONDS);
        x.onBackpressureBuffer().subscribe(System.out::println);
    }

    public static void demoBackpressureDrop() {
        Observable<Long> x = Observable.interval(1, TimeUnit.SECONDS);
        x.onBackpressureDrop().subscribe(System.out::println);
    }
    public static void main(String[] args) {
        Observable<String> x = Observable.just("a", "b", "c");
        //
        x.onBackpressureLatest();
    }
}
