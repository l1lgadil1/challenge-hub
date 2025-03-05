package com.challengehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ChallengeHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChallengeHubApplication.class, args);
    }
} 