package com.example.reactiveprogramming.demo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class DemoUnsubscribe {
    public static void main(String[] args) {

        Flux<Integer> observable = Flux.just(10, 20, 30)
                .doOnSubscribe(subscription -> System.out.println("there is a new subscription"))
                .doOnNext(s -> {
                    System.out.println("haha "+ s);
                })
                .doOnComplete(() -> System.out.println("complete"));
        observable.subscribe(new Subscriber<Integer>() {
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                s.request(3);
                this.subscription = subscription;
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
                if (integer == 20) subscription.cancel();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error");
            }

            @Override
            public void onComplete() {
                System.out.println("complete2222");
            }
        });
    }
}
