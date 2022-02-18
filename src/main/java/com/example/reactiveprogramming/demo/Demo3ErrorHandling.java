package com.example.reactiveprogramming.demo;

import rx.Observable;

public class Demo3ErrorHandling {

    public static void exceptionExample() {
        Observable<String> people = Observable.just("A", "B", "C")
                .concatWith(Observable.error(new RuntimeException("this is an error")))
                .concatWith(Observable.just("D", "E", "F"));
        people.subscribe(System.out::println);
    }

    public static void exceptionDownStreamHandling() {
        Observable<String> people = Observable.just("A", "B", "C")
                .concatWith(Observable.error(new RuntimeException("this is an error")))
                .concatWith(Observable.just("D", "E", "F"));
        people.subscribe(System.out::println, throwable -> System.out.println(throwable.getMessage()));
    }

    public static void exceptionHandleError() {
        Observable.just("A", "B", "C")
                .concatWith(Observable.error(new RuntimeException("this is an error")))
                .onErrorReturn(throwable -> "new value")
                .subscribe(System.out::println);
    }

    public static void exceptionCallABackup() {
        Observable<String> people = Observable.just("A", "B", "C")
                .concatWith(Observable.error(new RuntimeException("this is an error")))
                .concatWith(Observable.just("D", "E", "F"));
        people.onErrorResumeNext(throwable -> {
                    System.out.println("switch to backup server");
                    return Observable.just("a", "b", "c");
                })
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        exceptionHandleError();
    }
}
