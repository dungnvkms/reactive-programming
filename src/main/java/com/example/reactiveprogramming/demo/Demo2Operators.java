package com.example.reactiveprogramming.demo;

import reactor.core.publisher.Flux;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

public class Demo2Operators {
    public static void main(String[] args) {
        String allAlphabets = "the quick brown fox jumps over the lazy dog";
        List<String> words = Arrays.asList(allAlphabets.split(" "));

        Observable.from(words)
                .flatMap(word -> Observable.from(word.split("")))
                .distinct()
                .zipWith(Observable.range(1,50), (letter, line) -> line + " " + letter)
                .subscribe(System.out::println);
    }
}
