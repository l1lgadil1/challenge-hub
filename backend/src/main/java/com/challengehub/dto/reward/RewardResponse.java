package com.challengehub.dto.reward;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {
    private Long id;
    private String name;
    private String description;
    private String type;
    private Integer pointsValue;
    private LocalDateTime awardedAt;
} 