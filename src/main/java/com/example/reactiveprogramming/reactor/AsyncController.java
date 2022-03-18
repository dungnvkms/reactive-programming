package com.example.reactiveprogramming.reactor;

import com.example.reactiveprogramming.demo.helper.CommonUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CompletableFuture;

@RestController
public class AsyncController {
    @GetMapping("/async_result")
    @Async
    public CompletableFuture<String> getResultAsync() {
        CommonUtils.sleep(300);
        return CompletableFuture.completedFuture("Result is ready!");
    }
}