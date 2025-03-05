package com.challengehub.dto.reward;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardRequest {
    
    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    @NotBlank(message = "Description cannot be blank")
    private String description;
    
    @NotBlank(message = "Type cannot be blank")
    private String type; // BADGE, POINTS, etc.
    
    private Integer pointsValue; // For POINTS type
} 