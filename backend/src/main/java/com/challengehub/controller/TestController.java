package com.challengehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "Test", description = "Test endpoints to verify application setup")
public class TestController {

    @Operation(
        summary = "Test endpoint",
        description = "Returns a simple message to verify the application is running"
    )
    @GetMapping
    public String test() {
        return "ChallengeHub API is running!";
    }
} 