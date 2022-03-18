package com.example.reactiveprogramming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Flux;

@EnableAsync
@SpringBootApplication
public class ReactiveProgrammingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveProgrammingApplication.class, args);

//        Flux flux = Flux.just("hello", "world")
//                .concatWith(Flux.error(new RuntimeException("this is an error")))
//                .concatWith(Flux.just("after error"))
//                .log();
//        flux.subscribe(System.out::println, System.out::println);
    }

}
