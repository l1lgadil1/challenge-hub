package com.challengehub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_rewards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReward {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id", nullable = false)
    private Reward reward;
    
    @Column(nullable = false)
    private LocalDateTime awardedAt;
    
    @PrePersist
    protected void onCreate() {
        awardedAt = LocalDateTime.now();
    }
} 