package com.example.reactiveprogramming.demo;


import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import java.util.Arrays;
import java.util.List;

public class Sample {
    public static void main(String[] args) {
        List<String> symbols = Arrays.asList("John", "Foo", "Bar", "Tom");

        Observable<String> feed = Observable.just("John", "Foo", "Bar", "Tom");
//        feed.subscribe(System.out::println,
//                error -> System.err.println("Error " + error),
//                () -> {
//                    System.out.println("Done");
//                });
        Subscription x = feed.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
//                if (s.equals("AAPL")) {
//                    unsubscribe();
//                }
            }
        });
//
//        feed.subscribe(System.out::println);
    }
}
