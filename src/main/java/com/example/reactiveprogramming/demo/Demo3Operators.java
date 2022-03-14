package com.example.reactiveprogramming.demo;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;

public class Demo3Operators {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog");

        Observable.fromIterable(words)
                .filter(item -> item.equals("the"))
                .flatMap(word -> Observable.fromArray(word.split("")))
                .distinct()
                .subscribe(System.out::println);
    }
}
