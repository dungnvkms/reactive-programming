package com.example.reactiveprogramming.demo;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class Demo2Operators {
    public static void main(String[] args) {
        String allAlphabets = "the quick brown fox jumps over the lazy dog";
        List<String> words = Arrays.asList(allAlphabets.split(" "));

        Flux.fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .distinct()
                .zipWith(Flux.range(1,50), (letter, line) -> line + " " + letter)
                .subscribe(System.out::println);
    }
}
