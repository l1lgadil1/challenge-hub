package com.challengehub.dto.reward;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardRewardRequest {
    
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    
    @NotNull(message = "Reward ID cannot be null")
    private Long rewardId;
} 