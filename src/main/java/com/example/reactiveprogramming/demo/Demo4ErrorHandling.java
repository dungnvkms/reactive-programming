package com.example.reactiveprogramming.demo;

import rx.Observable;
import rx.Subscriber;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Demo4ErrorHandling {

    public static void exceptionExample() {
        Observable<String> people = Observable.just("A", "B", "C")
                .concatWith(Observable.error(new RuntimeException("this is an exception")))
                .concatWith(Observable.just("D", "E", "F"));
        people.subscribe(System.out::println);
    }

    public static void exceptionDownStreamHandling() {
        Observable<String> people = Observable.just("A", "B", "C")
                .concatWith(Observable.error(new RuntimeException("this is an exception")))
                .concatWith(Observable.just("D", "E", "F"));
        people.subscribe(System.out::println, throwable -> System.out.println(throwable.getMessage()));
    }

    public static void exceptionUpperStreamHandling() {
        Observable.just("A", "B", "C")
                .concatWith(Observable.error(new RuntimeException("this is an error")))
                .doOnError(throwable -> System.out.println("logged by server"))
                .subscribe(System.out::println);
    }

    public static void exceptionHandleError() {
        Observable.just("A", "B", "C")
                .concatWith(Observable.error(new RuntimeException("this is an exception")))
                .onErrorReturn(throwable -> "new value")
                .subscribe(System.out::println);
    }

    public static void exceptionCallABackup() {
        Observable<String> people = Observable.just("A", "B", "C")
                .concatWith(Observable.error(new OutOfMemoryError("this is an error")))
                .concatWith(Observable.just("D", "E", "F"));
        people.onErrorResumeNext(throwable -> {
                    System.out.println("switch to backup server");
                    return Observable.just("a", "b", "c");
                })
                .subscribe(System.out::println);
    }

    public static void exceptionRetry() {
        // resubscribe if there are errors
        Observable<String> people = Observable.just("A", "B", "C")
                .concatWith(Observable.error(new RuntimeException("this is an error")))
                .concatWith(Observable.just("D", "E", "F"));
        people.retryWhen(observable -> observable.take(10).delay(2, TimeUnit.SECONDS))
                .subscribe(System.out::println);
    }

    public static void exceptionVsError() {
        Observable<String> exception = Observable.<String>error(new IOException())
                .onExceptionResumeNext(Observable.just("This value will be used to recover from the IOException"));

        Observable<String> data = Observable.just("A", "B");
        Observable<String> error = Observable.<String>error(new Error())
                .onExceptionResumeNext(Observable.just("This value will not be used"));

        Observable.concat(exception, data, error)
                .subscribe(
                        message -> System.out.println("onNext: " + message),
                        err -> System.err.println("onError: " + err));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        exceptionVsError();

        Thread.sleep(3000);
    }
}
