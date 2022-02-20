package com.example.reactiveprogramming.demo;

import rx.BackpressureOverflow;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Demo5Backpressure {

    public static void demoBackpressureSkippingItems() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6);
        observable.skip(2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Integer value) {
                System.out.println(value);
            }
        });
    }

    public static void demoBackpressureBatchingEmittedItems() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6);
        observable.buffer(4).subscribe(new Subscriber<List<Integer>>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(List<Integer> value) {
                System.out.println(value);
            }
        });
    }

    public static void demoBackpressureRequest() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6);
        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Integer value) {
                if (value == 2) unsubscribe();
                System.out.println(value);
            }
        });
    }

    public static void demoBackpressureUnsubscribe() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6);
        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Integer value) {
                System.out.println(value);
                if (value == 2) unsubscribe();
            }
        });
    }

    public static void demoBackpressureBuffer() {
        Observable<Long> observable = Observable.interval(1, TimeUnit.MILLISECONDS);
        observable.onBackpressureBuffer(10, () -> {}, BackpressureOverflow.ON_OVERFLOW_DROP_LATEST)
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                        sleep(100);
                    }
                });
        sleep(1000000);
    }

    public static void demoBackpressureDrop() {
        Observable<Long> observable = Observable.interval(1, TimeUnit.MILLISECONDS);
        observable.onBackpressureDrop(v -> System.out.println("Dropped.. :" + v))
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                        sleep(100);
                    }
                });
        sleep(1000000);
    }


    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        demoBackpressureSkippingItems();
    }
}
