package com.example.reactiveprogramming.demo;

import rx.Observable;

public class Demo1Async {
    // every unit is a node
    public static void main(String[] args) {
        Observable<String> baker = Observable.just("I am a Baker");
        Observable<String> waiter = Observable.just("I am a Waiter");
        Observable<String> client = Observable.just("I am a Client");
    }
}
