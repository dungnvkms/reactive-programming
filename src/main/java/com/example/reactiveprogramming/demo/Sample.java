package com.example.reactiveprogramming.demo;

import io.reactivex.rxjava3.core.Observable;

public class Sample {
    public static void main(String[] args) {
        Observable<String> feed = Observable.just("John", "Foo", "Bar", "Tom");
        feed.subscribe(System.out::println,
                error -> System.err.println("Error " + error),
                () -> {
                    System.out.println("Done");
                });
    }
}
