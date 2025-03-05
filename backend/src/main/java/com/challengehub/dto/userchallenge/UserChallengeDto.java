package com.challengehub.dto.userchallenge;

import com.challengehub.dto.challenge.ChallengeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChallengeDto {
    private Long id;
    private ChallengeDto challenge;
    private Integer progress;
    private Boolean completed;
    private LocalDateTime startDate;
} 