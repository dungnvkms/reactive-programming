package com.example.reactiveprogramming.demo;

import com.example.reactiveprogramming.demo.helper.CommonUtils;
import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.util.concurrent.TimeUnit;

public class Demo5Backpressure {

    public static void demoBackpressureSkippingItems() {
        Flowable<Integer> flowable = Flowable.just(1, 2, 3, 4, 5, 6);
        flowable.skip(2).subscribe(System.out::println);
    }

    public static void demoBackpressureBatchingEmittedItems() {
        Flowable<Integer> flowable = Flowable.just(1, 2, 3, 4, 5, 6);
        flowable.buffer(4).subscribe(System.out::println);
    }

    public static void demoBackpressureRequest() {
        Flowable<Integer> flowable = Flowable.just(1, 2, 3, 4, 5, 6);
        flowable.subscribe(new Subscriber<Integer>() {
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                this.subscription = s;
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(Integer value) {
                System.out.println(value);
                this.subscription.request(1);
                if (value == 2) this.subscription.cancel();
            }
        });
    }

    public static void demoBackpressureBuffer() {
        Flowable<Long> flowable = Flowable.interval(1, TimeUnit.MILLISECONDS);
        flowable.onBackpressureBuffer(1, () -> {}, BackpressureOverflowStrategy.DROP_LATEST)
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Long>() {
                    private Subscription subscription;
                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                        this.subscription.request(1);
                        CommonUtils.sleep(100);
                    }
                });
    }

    public static void demoBackpressureDrop() {
        Flowable<Long> flowable = Flowable.interval(1, TimeUnit.MILLISECONDS);
        flowable.onBackpressureDrop(v -> System.out.println("Dropped.. :" + v))
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Long>() {
                    private Subscription subscription;
                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                        this.subscription.request(1);
                        CommonUtils.sleep(100);
                    }
                });
    }


    public static void main(String[] args) {
        demoBackpressureBuffer();
        CommonUtils.sleep(1000000);
    }
}
