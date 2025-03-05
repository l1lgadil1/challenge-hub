package com.challengehub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_challenges")
public class UserChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @NotNull
    private Integer progress;

    private Boolean completed;
    
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @PrePersist
    protected void onCreate() {
        if (progress == null) {
            progress = 0;
        }
        if (completed == null) {
            completed = false;
        }
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
    }
} 