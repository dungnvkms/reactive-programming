package com.example.reactiveprogramming.demo;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.stream.Stream;

public class Demo6Schedulers {

    public static void myPublishSchedulers(Scheduler scheduler) {
        Observable.range(0, 2)
                .observeOn(scheduler)
                .map(i -> {
                    System.out.println("Mapping for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                }).subscribe();
    }

    public static void myPublishMultipleSchedulers() {
        Observable.range(0, 2)
                .map(i -> {
                    System.out.println("Mapping one for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                })
                .observeOn(Schedulers.immediate())
                .map(i -> {
                    System.out.println("Mapping two for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                })
                .observeOn(Schedulers.io())
                .map(i -> {
                    System.out.println("Mapping three for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                }).subscribe();
    }

    public static void mySubscribersSchedulers(Scheduler scheduler) throws InterruptedException {
        Observable.just("a", "b", "c")
                .doOnNext(v -> System.out.println("before observeOn: " + Thread.currentThread().getName()))
                .observeOn(Schedulers.immediate())
                .doOnNext(v -> System.out.println("after observeOn: " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.computation())
                .subscribe(v -> System.out.println("received " + v + " on " + Thread.currentThread().getName()));
    }

    public static void main(String[] args) throws InterruptedException {
//        myPublishSchedulers(Schedulers.immediate());
//        myPublishSchedulers(Schedulers.single());
//        myPublishSchedulers(Schedulers.newSingle("myThread"));
//        myPublishSchedulers(Schedulers.boundedElastic());
//        myPublishSchedulers(Schedulers.parallel());
//        myPublishMultipleSchedulers();
//        mySubscribersSchedulers(Schedulers.single());

        Thread.sleep(5000);

    }
}
