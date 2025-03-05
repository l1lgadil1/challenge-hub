package com.challengehub.controller;

import com.challengehub.dto.challenge.ChallengeDto;
import com.challengehub.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
@Tag(name = "Challenges", description = "Endpoints for managing challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Operation(
        summary = "Get all challenges",
        description = "Retrieves a list of all available challenges"
    )
    @GetMapping
    public ResponseEntity<List<ChallengeDto>> getAllChallenges() {
        return ResponseEntity.ok(challengeService.getAllChallenges());
    }

    @Operation(
        summary = "Get challenge by ID",
        description = "Retrieves a challenge by its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDto> getChallengeById(@PathVariable Long id) {
        return ResponseEntity.ok(challengeService.getChallengeById(id));
    }

    @Operation(
        summary = "Get challenges by category",
        description = "Retrieves a list of challenges by category"
    )
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ChallengeDto>> getChallengesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(challengeService.getChallengesByCategory(category));
    }

    @Operation(
        summary = "Create a new challenge",
        description = "Creates a new challenge (admin only)",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ChallengeDto> createChallenge(@Valid @RequestBody ChallengeDto challengeDto) {
        return new ResponseEntity<>(challengeService.createChallenge(challengeDto), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update a challenge",
        description = "Updates an existing challenge (admin only)",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ChallengeDto> updateChallenge(
            @PathVariable Long id,
            @Valid @RequestBody ChallengeDto challengeDto) {
        return ResponseEntity.ok(challengeService.updateChallenge(id, challengeDto));
    }

    @Operation(
        summary = "Delete a challenge",
        description = "Deletes a challenge by its ID (admin only)",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
        challengeService.deleteChallenge(id);
        return ResponseEntity.noContent().build();
    }
} 