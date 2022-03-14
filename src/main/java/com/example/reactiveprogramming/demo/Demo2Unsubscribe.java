package com.example.reactiveprogramming.demo;

import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class Demo2Unsubscribe {
    public static void main(String[] args) {
        Flowable<Integer> observable = Flowable.just(10, 20, 30, 40)
                .doOnNext(s -> System.out.println("map "+ s))
                .doOnComplete(() -> System.out.println("complete"));
        observable.subscribe(new Subscriber<Integer>() {
            private Subscription s;
            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
                if (integer == 20) s.cancel();
                else this.s.request(1);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
