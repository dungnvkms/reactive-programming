package com.example.reactiveprogramming.demo;

import com.example.reactiveprogramming.demo.helper.Color;
import com.example.reactiveprogramming.demo.helper.CommonUtils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Demo0PubSub {

    public static void demo() {
        String allAlphabets = "the quick brown fox jumps over the lazy dog";
        // assume this is an async data event list returned from API
        List<String> words = Arrays.asList(allAlphabets.split(" "));

        // and then we apply all these operators for each async event
        Observable.fromIterable(words)
                .distinct()
                .subscribe(System.out::println);
    }

    public static void coldPublishers() {
        // Start a cold Publisher which emits 0,1,2 every sec.
        Observable<Long> observable =  Observable.interval(1, TimeUnit.SECONDS);
        observable.subscribe(i -> System.out.println(Color.ANSI_GREEN + "first_subscriber:" + i + " on Thread: " + Thread.currentThread().getName()));
        CommonUtils.sleep(3000);
        observable.subscribe(i -> System.out.println(Color.ANSI_YELLOW +"second_subscriber:" + i + " on Thread: " + Thread.currentThread().getName()));
    }

    public static void hotPublishers() throws InterruptedException {
        // Start a cold Publisher which emits 0,1,2 every sec.
        Observable<Long> observable =  Observable.interval(1, TimeUnit.SECONDS);
        // Make the Publisher Hot
        ConnectableObservable<Long> connectableObservable = observable.publish();
        // Now that we have a handle on the hot Publisher
        // Let's subscribe to that with multiple subscribers.
        connectableObservable.subscribe(i -> System.out.println(Color.ANSI_GREEN + "first_subscriber received value:" + i));
        // Start firing events with .connect() on the published observable.
        connectableObservable.connect();
        Thread.sleep(5000);
        // Let a second subscriber come after some time 3 secs here.
        connectableObservable.subscribe(i -> System.out.println(Color.ANSI_YELLOW +"second_subscriber received value:" + i));
        Thread.sleep(5000);
    }

    public static void publishOnMainThread() {
        final Observable<String> observable = Observable.just("Hello from thread: ");
        observable
                .map(item -> {
                    System.out.println("mapping: " + Thread.currentThread().getName());
                    return item;
                })
                .subscribe(v -> System.out.println(v + Thread.currentThread().getName()));
    }

    public static void publisherOnSeparateThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            final Observable<String> observable = Observable.just("Hello from thread: ");
            observable.subscribe(v -> System.out.println(v + Thread.currentThread().getName()));
        });
        t.start();
        t.join();
    }

    public static void main(String[] args) throws InterruptedException {
        publishOnMainThread();
        CommonUtils.sleep(20000);
    }
}
