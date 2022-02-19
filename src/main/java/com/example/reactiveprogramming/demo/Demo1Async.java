package com.example.reactiveprogramming.demo;

import rx.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo1Async {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static Future<Integer> getUserId(int input) {
        return executor.submit(() -> {
            if (input == 10) throw  new Exception("Can't handle");
            return input;
        });
    }

    public static Future<String> getUserInfo(int input) {
        return executor.submit(() -> "John" + input);
    }

    public static void runWithFuture(int userInput) throws ExecutionException, InterruptedException {
        Future<Integer> userId = getUserId(userInput);
        while (!userId.isDone()) {
            if (userId.get() == null) {
                throw new RuntimeException("User not found");
            }
            Future<String> userInfo = getUserInfo(userId.get());
            while (!userInfo.isDone()) {
                if (userInfo.get() == null) {
                    throw new RuntimeException("User not found");
                }
                System.out.println(userInfo.get());
            }
        }
    }

    public static void runWithReactive(int userInput) {
        Observable.from(getUserId(userInput))
                .flatMap(id -> Observable.from(getUserInfo(id)))
                .subscribe(System.out::println, throwable -> System.out.println(throwable.getMessage()));
    }

    public static void main(String[] args) {
        runWithReactive(15);
        executor.shutdown();
    }
}
