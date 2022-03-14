package com.example.reactiveprogramming.demo;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.example.reactiveprogramming.demo.helper.Color.ANSI_BLUE;
import static com.example.reactiveprogramming.demo.helper.Color.ANSI_GREEN;
import static com.example.reactiveprogramming.demo.helper.Color.ANSI_YELLOW;

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
                    System.out.println(ANSI_BLUE + "Mapping one for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                })
                .observeOn(Schedulers.computation())
                .map(i -> {
                    System.out.println(ANSI_YELLOW + "Mapping two for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                })
                .observeOn(Schedulers.io())
                .map(i -> {
                    System.out.println(ANSI_GREEN + "Mapping three for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                }).subscribe();
    }

    public static void mySubscribersSchedulers() throws InterruptedException {
        Observable.just("a", "b", "c")
                .doOnNext(v -> System.out.println(ANSI_BLUE + "before observeOn: " + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .doOnNext(v -> System.out.println(ANSI_YELLOW + "after observeOn: " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.computation())
                .subscribe(v -> System.out.println(ANSI_YELLOW + "received " + v + " on " + Thread.currentThread().getName()));
    }

    public static void main(String[] args) throws InterruptedException {
//        myPublishSchedulers(Schedulers.immediate());
//        myPublishSchedulers(Schedulers.single());
//        myPublishSchedulers(Schedulers.newSingle("myThread"));
//        myPublishSchedulers(Schedulers.boundedElastic());
//        myPublishSchedulers(Schedulers.parallel());
//        myPublishMultipleSchedulers();
//        mySubscribersSchedulers(Schedulers.single());

        myPublishMultipleSchedulers();

        Thread.sleep(5000);

    }
}
