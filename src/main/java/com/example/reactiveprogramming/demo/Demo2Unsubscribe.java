package com.example.reactiveprogramming.demo;

import rx.Observable;
import rx.Subscriber;

public class Demo2Unsubscribe {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.just(10, 20, 30)
                .doOnNext(s -> System.out.println("map "+ s))
                .doOnCompleted(() -> System.out.println("complete"));
        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
                if (integer == 20) unsubscribe();
            }
        });
    }
}
