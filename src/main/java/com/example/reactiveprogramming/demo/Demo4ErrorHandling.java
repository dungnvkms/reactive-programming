package com.example.reactiveprogramming.demo;

import com.example.reactiveprogramming.demo.helper.CommonUtils;
import io.reactivex.rxjava3.core.Observable;

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
        people.retryWhen(observable -> observable.take(10).delay(5, TimeUnit.SECONDS))
                .subscribe(System.out::println);
    }

    public static void exceptionComplete() {
        Observable<String> exception = Observable.<String>error(new IOException())
                .onErrorResumeNext(throwable -> Observable.just("This value will be used to recover from the IOException"));
        Observable<String> data1 = Observable.just("A", "B");
        Observable<String> error = Observable.<String>error(new Error());
        Observable<String> data2 = Observable.just("C", "D");

        Observable.concat(exception, data1, error, data2)
                .onErrorComplete()
                .subscribe(
                        message -> System.out.println("onNext: " + message),
                        err -> System.err.println("onError: " + err));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        exceptionComplete();

        CommonUtils.sleep(20000);
    }
}
