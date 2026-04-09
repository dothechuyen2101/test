package com.banking.ratelimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.banking.ratelimit", "com.banking.core"})
public class RateLimitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateLimitServiceApplication.class, args);
    }
}
