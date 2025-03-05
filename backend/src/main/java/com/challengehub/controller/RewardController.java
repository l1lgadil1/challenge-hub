package com.challengehub.controller;

import com.challengehub.dto.reward.AwardRewardRequest;
import com.challengehub.dto.reward.RewardRequest;
import com.challengehub.dto.reward.RewardResponse;
import com.challengehub.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Rewards", description = "Endpoints for managing user rewards")
public class RewardController {

    private final RewardService rewardService;

    @Operation(
        summary = "Get user rewards",
        description = "Returns a list of rewards earned by the authenticated user"
    )
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RewardResponse>> getUserRewards() {
        return ResponseEntity.ok(rewardService.getUserRewards());
    }

    @Operation(
        summary = "Get reward by ID",
        description = "Returns the details of a specific reward"
    )
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RewardResponse> getRewardById(@PathVariable Long id) {
        return ResponseEntity.ok(rewardService.getRewardById(id));
    }

    @Operation(
        summary = "Create a reward",
        description = "Creates a new reward type (admin only)"
    )
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RewardResponse> createReward(@Valid @RequestBody RewardRequest rewardRequest) {
        return ResponseEntity.ok(rewardService.createReward(rewardRequest));
    }

    @Operation(
        summary = "Award a reward to a user",
        description = "Grants a reward to a user (admin only)"
    )
    @PostMapping("/award")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RewardResponse> awardReward(@Valid @RequestBody AwardRewardRequest awardRewardRequest) {
        return ResponseEntity.ok(rewardService.awardReward(awardRewardRequest));
    }
} 