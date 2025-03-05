package com.challengehub.dto.userchallenge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeProgressDto {
    private Long id;
    private String title;
    private String category;
    private LocalDateTime startDate;
    private LocalDateTime completionDate;
    private Integer durationDays;
    private Integer currentDay;
    private Integer progress;
    private Boolean completed;
    private List<DayStatusDto> dayStatuses;
    private Integer daysCompleted;
    private Integer daysRemaining;
    private Integer overdueDays;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DayStatusDto {
        private Integer day;
        private String status; // "completed", "current", "upcoming", "overdue"
    }
} 