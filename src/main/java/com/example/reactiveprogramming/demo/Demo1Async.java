package com.example.reactiveprogramming.demo;

import com.example.reactiveprogramming.demo.helper.CommonUtils;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo1Async {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static Future<Integer> getUserId(Integer input) {
        return executor.submit(() -> {
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
        Observable.fromFuture(getUserId(userInput))
                .flatMap(id -> Observable.fromFuture(getUserInfo(id)))
                .subscribe(System.out::println, throwable -> System.out.println(throwable.getMessage()));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        runWithReactive(10);
//        runWithCompletableFuture().thenRun(() -> System.out.println("this is an async"));
        executor.shutdown();
        CommonUtils.sleep(10000);
    }
}
