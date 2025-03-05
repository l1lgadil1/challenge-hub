package com.challengehub.dto.comment;

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
public class CommentRequest {
    
    @NotNull(message = "Challenge ID cannot be null")
    private Long challengeId;
    
    @NotBlank(message = "Content cannot be blank")
    private String content;
} 