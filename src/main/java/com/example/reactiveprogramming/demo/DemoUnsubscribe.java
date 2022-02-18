package com.example.reactiveprogramming.demo;

import org.reactivestreams.Subscription;
import rx.Observable;
import rx.Subscriber;

public class DemoUnsubscribe {
    public static void main(String[] args) {

        Observable<Integer> observable = Observable.just(10, 20, 30)
//                .doOnSubscribe(s -> {
//                    System.out.println("haha "+ s);
//                })
                .doOnNext(s -> {
                    System.out.println("haha "+ s);
                })
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

            }
        });
    }
}
