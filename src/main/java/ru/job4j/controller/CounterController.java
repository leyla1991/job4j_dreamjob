package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@RestController
public class CounterController {

    private final AtomicInteger total = new AtomicInteger();

    @GetMapping("/count")
    public String count() {
        int value = total.incrementAndGet();
        return String.format("Total execute: %d", value);
    }

}
