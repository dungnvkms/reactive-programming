package com.example.reactiveprogramming.demo;

import rx.Observable;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Demo1Async {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static Future<Integer> getUserId(Integer input) {
        return executor.submit(() -> {
            System.out.println("dvnguyen11111111");
            if (input == 10) throw new Exception("Can't handle");
            return input;
        });
    }

    public static Future<String> getUserInfo(int input) {
        return executor.submit(() -> "John" + input);
    }

    public static void runWithFuture(Integer userInput) throws ExecutionException, InterruptedException {
        Future<Integer> userId = getUserId(userInput);
        while (!userId.isDone()) {
            if (userId.get() == null) {
                executor.shutdown();
                throw new RuntimeException("User not found");
            }
            Future<String> userInfo = getUserInfo(userId.get());
            while (!userInfo.isDone()) {
                if (userInfo.get() == null) {
                    executor.shutdown();
                    throw new RuntimeException("User not found");
                }
                System.out.println(userInfo.get());
            }
        }
    }

    public static int compute(int n) {
        if (n == 10) {
            throw new RuntimeException("User not found");
        }
        return n;
    }

    public static CompletableFuture<Void> runWithCompletableFuture() {
        return CompletableFuture.supplyAsync(() -> compute(10))
                .exceptionally(throwable -> {
                    System.out.println("User not found");
                    return -1;
                })
                .thenAccept(data -> System.out.println("user infor" + data))
                .exceptionally(throwable -> {
                    throw new RuntimeException("User not found");
                });
    }

    public static void runWithReactive(Integer userInput) {
        Observable.from(getUserId(userInput))
                .flatMap(id -> Observable.from(getUserInfo(id)))
                .subscribe(System.out::println, throwable -> System.out.println(throwable.getMessage()));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        runWithCompletableFuture().thenRun(() -> System.out.println("this is an async"));
        executor.shutdown();
        Thread.sleep(10000);
    }
}
