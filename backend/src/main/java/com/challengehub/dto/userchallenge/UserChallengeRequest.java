package com.challengehub.dto.userchallenge;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChallengeRequest {
    @NotNull(message = "Challenge ID is required")
    private Long challengeId;
    
    private Integer progress;
    private Boolean completed;
    private LocalDateTime startDate;
} 