package com.example.reactiveprogramming.demo;

import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.TimeUnit;

public class Demo2Backpressure {
    // batching emitted items
    // skipping elements

    public static void demoBackpressureUnsubscribe() {
        Observable<Long> x = Observable.interval(1, TimeUnit.SECONDS);
        x.subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Long aLong) {
                if (aLong == 10) unsubscribe();
            }
        });
    }


    public static void demoBackpressureBuffer() {
        Observable<Long> x = Observable.interval(1, TimeUnit.SECONDS);
        x.onBackpressureBuffer().subscribe(System.out::println);
    }

    public static void demoBackpressureDrop() {
        Observable<Long> x = Observable.interval(1, TimeUnit.SECONDS);
        x.onBackpressureDrop().subscribe(System.out::println);
    }
    public static void main(String[] args) {
    }
}
