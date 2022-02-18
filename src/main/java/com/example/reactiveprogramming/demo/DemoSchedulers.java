package com.example.reactiveprogramming.demo;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class DemoSchedulers {

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
        // xem lại chỗ này chưa chạy
        Observable.just("a", "b", "c") //this is where subscription triggers data production
                //this is influenced by subscribeOn
                .doOnNext(v -> System.out.println("before observeOn: " + Thread.currentThread().getName()))
                .observeOn(Schedulers.immediate())
                //the rest is influenced by observeOn
                .doOnNext(v -> System.out.println("after observeOn: " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.computation())
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


        Observable.range(0, 10)
                .map(i -> {
                    System.out.println("Mapping for " + i + " is done by thread " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return i;
                })
                .doOnNext(v -> System.out.println("before observeOn: " + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                //the rest is influenced by observeOn
                .doOnNext(v -> System.out.println("after observeOn: " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.immediate())
                .subscribe();
        Thread.sleep(2000);
    }
}
