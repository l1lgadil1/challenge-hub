package com.challengehub.service.impl;

import com.challengehub.dto.challenge.ChallengeDto;
import com.challengehub.dto.userchallenge.ChallengeProgressDto;
import com.challengehub.dto.userchallenge.UserChallengeDto;
import com.challengehub.dto.userchallenge.UserChallengeRequest;
import com.challengehub.exception.ResourceNotFoundException;
import com.challengehub.model.Challenge;
import com.challengehub.model.User;
import com.challengehub.model.UserChallenge;
import com.challengehub.repository.ChallengeRepository;
import com.challengehub.repository.UserChallengeRepository;
import com.challengehub.repository.UserRepository;
import com.challengehub.service.UserChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UserChallengeServiceImpl implements UserChallengeService {

    private final UserChallengeRepository userChallengeRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    @Override
    public List<UserChallengeDto> getUserChallenges() {
        User currentUser = getCurrentUser();
        return userChallengeRepository.findByUser(currentUser).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserChallengeDto> getUserChallengesByCompleted(Boolean completed) {
        User currentUser = getCurrentUser();
        return userChallengeRepository.findByUserAndCompleted(currentUser, completed).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserChallengeDto getUserChallengeById(Long id) {
        User currentUser = getCurrentUser();
        UserChallenge userChallenge = userChallengeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserChallenge", "id", id));
        
        // Ensure the user challenge belongs to the current user
        if (!userChallenge.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You don't have access to this challenge");
        }
        
        return mapToDto(userChallenge);
    }

    @Override
    public UserChallengeDto startChallenge(UserChallengeRequest request) {
        User currentUser = getCurrentUser();
        Challenge challenge = challengeRepository.findById(request.getChallengeId())
                .orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", request.getChallengeId()));
        
        // Check if the user has already started this challenge
        if (userChallengeRepository.findByUserAndChallenge(currentUser, challenge).isPresent()) {
            throw new IllegalArgumentException("You have already started this challenge");
        }
        
        UserChallenge userChallenge = UserChallenge.builder()
                .user(currentUser)
                .challenge(challenge)
                .progress(0)
                .completed(false)
                .startDate(request.getStartDate() != null ? request.getStartDate() : java.time.LocalDateTime.now())
                .build();
        
        UserChallenge savedUserChallenge = userChallengeRepository.save(userChallenge);
        return mapToDto(savedUserChallenge);
    }

    @Override
    public UserChallengeDto updateUserChallenge(Long id, UserChallengeRequest request) {
        User currentUser = getCurrentUser();
        UserChallenge userChallenge = userChallengeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserChallenge", "id", id));
        
        // Ensure the user challenge belongs to the current user
        if (!userChallenge.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You don't have access to this challenge");
        }
        
        if (request.getProgress() != null) {
            userChallenge.setProgress(request.getProgress());
        }
        
        if (request.getCompleted() != null) {
            userChallenge.setCompleted(request.getCompleted());
        }
        
        UserChallenge updatedUserChallenge = userChallengeRepository.save(userChallenge);
        return mapToDto(updatedUserChallenge);
    }

    @Override
    public void deleteUserChallenge(Long id) {
        User currentUser = getCurrentUser();
        UserChallenge userChallenge = userChallengeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserChallenge", "id", id));
        
        // Ensure the user challenge belongs to the current user
        if (!userChallenge.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You don't have access to this challenge");
        }
        
        userChallengeRepository.delete(userChallenge);
    }

    @Override
    public ChallengeProgressDto getChallengeProgress(Long id) {
        User currentUser = getCurrentUser();
        UserChallenge userChallenge = userChallengeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserChallenge", "id", id));
        
        // Verify that the user challenge belongs to the current user
        if (!userChallenge.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You don't have access to this challenge");
        }
        
        Challenge challenge = userChallenge.getChallenge();
        LocalDateTime startDate = userChallenge.getStartDate();
        LocalDateTime now = LocalDateTime.now();
        
        // Calculate days since start
        long daysSinceStart = startDate != null ? 
                ChronoUnit.DAYS.between(startDate.toLocalDate(), now.toLocalDate()) : 0;
        
        // Calculate current day (1-indexed)
        int currentDay = (int) daysSinceStart + 1;
        
        // Ensure current day doesn't exceed duration
        currentDay = Math.min(currentDay, challenge.getDurationDays());
        
        // Calculate expected completion date
        LocalDateTime expectedCompletionDate = startDate != null ?
                startDate.plusDays(challenge.getDurationDays()) : null;
        
        // Calculate days completed, remaining, and overdue
        int daysCompleted = Math.min(currentDay - 1, userChallenge.getProgress());
        int daysRemaining = challenge.getDurationDays() - daysCompleted;
        
        // Calculate overdue days
        int overdueDays = 0;
        if (expectedCompletionDate != null && now.isAfter(expectedCompletionDate) && !userChallenge.getCompleted()) {
            overdueDays = (int) ChronoUnit.DAYS.between(expectedCompletionDate.toLocalDate(), now.toLocalDate());
        }
        
        // Store the final current day value
        final int finalCurrentDay = currentDay;
        
        // Generate day statuses
        List<ChallengeProgressDto.DayStatusDto> dayStatuses = IntStream.rangeClosed(1, challenge.getDurationDays())
                .mapToObj(day -> {
                    String status;
                    if (day <= userChallenge.getProgress()) {
                        status = "completed";
                    } else if (day == finalCurrentDay && !userChallenge.getCompleted()) {
                        status = "current";
                    } else if (day > finalCurrentDay) {
                        status = "upcoming";
                    } else {
                        status = "overdue";
                    }
                    
                    return ChallengeProgressDto.DayStatusDto.builder()
                            .day(day)
                            .status(status)
                            .build();
                })
                .collect(Collectors.toList());
        
        // Build and return the progress DTO
        return ChallengeProgressDto.builder()
                .id(userChallenge.getId())
                .title(challenge.getTitle())
                .category(challenge.getCategory())
                .startDate(startDate)
                .completionDate(userChallenge.getCompleted() ? 
                        startDate.plusDays(userChallenge.getProgress()) : null)
                .durationDays(challenge.getDurationDays())
                .currentDay(currentDay)
                .progress(userChallenge.getProgress())
                .completed(userChallenge.getCompleted())
                .dayStatuses(dayStatuses)
                .daysCompleted(daysCompleted)
                .daysRemaining(daysRemaining)
                .overdueDays(overdueDays)
                .build();
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
    
    private UserChallengeDto mapToDto(UserChallenge userChallenge) {
        return UserChallengeDto.builder()
                .id(userChallenge.getId())
                .challenge(mapChallengeToDto(userChallenge.getChallenge()))
                .progress(userChallenge.getProgress())
                .completed(userChallenge.getCompleted())
                .startDate(userChallenge.getStartDate())
                .build();
    }
    
    private ChallengeDto mapChallengeToDto(Challenge challenge) {
        return ChallengeDto.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .category(challenge.getCategory())
                .durationDays(challenge.getDurationDays())
                .build();
    }
} 