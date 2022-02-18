package com.example.reactiveprogramming.demo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class DemoSchedulers {

    public static void myPublishSchedulers(Scheduler scheduler) {
        Flux.range(0, 2)
                .publishOn(scheduler)
                .map(i -> {
                    System.out.println("Mapping for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                }).subscribe();
    }

    public static void myPublishMultipleSchedulers() {
        Flux.range(0, 2)
                .map(i -> {
                    System.out.println("Mapping one for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    System.out.println("Mapping two for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.parallel())
                .map(i -> {
                    System.out.println("Mapping three for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                }).subscribe();
    }

    public static void mySubscribersSchedulers(Scheduler scheduler) throws InterruptedException {
        // xem lại chỗ này chưa chạy
        Flux.just("a", "b", "c") //this is where subscription triggers data production
                //this is influenced by subscribeOn
                .doOnNext(v -> System.out.println("before publishOn: " + Thread.currentThread().getName()))
                .publishOn(Schedulers.elastic())
                //the rest is influenced by publishOn
                .doOnNext(v -> System.out.println("after publishOn: " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.parallel())
                .log()
                .subscribe(v -> System.out.println("received " + v + " on " + Thread.currentThread().getName()));
        Thread.sleep(5000);
    }

    public static void main(String[] args) throws InterruptedException {
//        myPublishSchedulers(Schedulers.immediate());
//        myPublishSchedulers(Schedulers.single());
//        myPublishSchedulers(Schedulers.newSingle("myThread"));
//        myPublishSchedulers(Schedulers.boundedElastic());
//        myPublishSchedulers(Schedulers.parallel());
//        myPublishMultipleSchedulers();
//        mySubscribersSchedulers(Schedulers.single());


        Flux.range(0, 10)
                .map(i -> {
                    System.out.println("Mapping for " + i + " is done by thread " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return i;
                })
                .doOnNext(v -> System.out.println("before publishOn: " + Thread.currentThread().getName()))
                .publishOn(Schedulers.parallel())
                //the rest is influenced by publishOn
                .doOnNext(v -> System.out.println("after publishOn: " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.newSingle("xxxxxxxx"))
                .subscribe();
        Thread.sleep(2000);
    }
}
