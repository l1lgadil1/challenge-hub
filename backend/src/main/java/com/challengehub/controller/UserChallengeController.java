package com.challengehub.controller;

import com.challengehub.dto.userchallenge.ChallengeProgressDto;
import com.challengehub.dto.userchallenge.UserChallengeDto;
import com.challengehub.dto.userchallenge.UserChallengeRequest;
import com.challengehub.service.UserChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-challenges")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Challenges", description = "Endpoints for managing user challenges")
public class UserChallengeController {

    private final UserChallengeService userChallengeService;

    @Operation(
        summary = "Get all user challenges",
        description = "Retrieves all challenges for the current user"
    )
    @GetMapping
    public ResponseEntity<List<UserChallengeDto>> getUserChallenges() {
        return ResponseEntity.ok(userChallengeService.getUserChallenges());
    }

    @Operation(
        summary = "Get user challenges by completion status",
        description = "Retrieves challenges for the current user filtered by completion status"
    )
    @GetMapping("/completed")
    public ResponseEntity<List<UserChallengeDto>> getUserChallengesByCompleted(
            @RequestParam(defaultValue = "true") Boolean completed) {
        return ResponseEntity.ok(userChallengeService.getUserChallengesByCompleted(completed));
    }

    @Operation(
        summary = "Get user challenge by ID",
        description = "Retrieves a specific challenge for the current user"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserChallengeDto> getUserChallengeById(@PathVariable Long id) {
        return ResponseEntity.ok(userChallengeService.getUserChallengeById(id));
    }

    @Operation(
        summary = "Start a challenge",
        description = "Starts a new challenge for the current user"
    )
    @PostMapping
    public ResponseEntity<UserChallengeDto> startChallenge(@Valid @RequestBody UserChallengeRequest request) {
        return new ResponseEntity<>(userChallengeService.startChallenge(request), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update user challenge",
        description = "Updates progress or completion status for a user challenge"
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserChallengeDto> updateUserChallenge(
            @PathVariable Long id,
            @Valid @RequestBody UserChallengeRequest request) {
        return ResponseEntity.ok(userChallengeService.updateUserChallenge(id, request));
    }

    @Operation(
        summary = "Delete user challenge",
        description = "Deletes a user challenge"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserChallenge(@PathVariable Long id) {
        userChallengeService.deleteUserChallenge(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Get challenge progress",
        description = "Retrieves detailed progress information for a specific user challenge"
    )
    @GetMapping("/{id}/progress")
    public ResponseEntity<ChallengeProgressDto> getChallengeProgress(@PathVariable Long id) {
        return ResponseEntity.ok(userChallengeService.getChallengeProgress(id));
    }
} 