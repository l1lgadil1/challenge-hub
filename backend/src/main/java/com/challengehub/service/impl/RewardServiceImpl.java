package com.challengehub.service.impl;

import com.challengehub.dto.reward.AwardRewardRequest;
import com.challengehub.dto.reward.RewardRequest;
import com.challengehub.dto.reward.RewardResponse;
import com.challengehub.exception.ResourceNotFoundException;
import com.challengehub.model.Reward;
import com.challengehub.model.User;
import com.challengehub.model.UserReward;
import com.challengehub.repository.RewardRepository;
import com.challengehub.repository.UserRepository;
import com.challengehub.repository.UserRewardRepository;
import com.challengehub.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;
    private final UserRewardRepository userRewardRepository;

    @Override
    public List<RewardResponse> getUserRewards() {
        User currentUser = getCurrentUser();
        
        return userRewardRepository.findByUser(currentUser).stream()
                .map(this::mapUserRewardToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RewardResponse getRewardById(Long id) {
        Reward reward = rewardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reward", "id", id));
        
        return mapRewardToResponse(reward);
    }

    @Override
    @Transactional
    public RewardResponse createReward(RewardRequest rewardRequest) {
        Reward reward = Reward.builder()
                .name(rewardRequest.getName())
                .description(rewardRequest.getDescription())
                .type(rewardRequest.getType())
                .pointsValue(rewardRequest.getPointsValue())
                .build();
        
        Reward savedReward = rewardRepository.save(reward);
        return mapRewardToResponse(savedReward);
    }

    @Override
    @Transactional
    public RewardResponse awardReward(AwardRewardRequest awardRewardRequest) {
        User user = userRepository.findById(awardRewardRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", awardRewardRequest.getUserId()));
        
        Reward reward = rewardRepository.findById(awardRewardRequest.getRewardId())
                .orElseThrow(() -> new ResourceNotFoundException("Reward", "id", awardRewardRequest.getRewardId()));
        
        UserReward userReward = UserReward.builder()
                .user(user)
                .reward(reward)
                .build();
        
        UserReward savedUserReward = userRewardRepository.save(userReward);
        return mapUserRewardToResponse(savedUserReward);
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
    
    private RewardResponse mapRewardToResponse(Reward reward) {
        return RewardResponse.builder()
                .id(reward.getId())
                .name(reward.getName())
                .description(reward.getDescription())
                .type(reward.getType())
                .pointsValue(reward.getPointsValue())
                .build();
    }
    
    private RewardResponse mapUserRewardToResponse(UserReward userReward) {
        return RewardResponse.builder()
                .id(userReward.getReward().getId())
                .name(userReward.getReward().getName())
                .description(userReward.getReward().getDescription())
                .type(userReward.getReward().getType())
                .pointsValue(userReward.getReward().getPointsValue())
                .awardedAt(userReward.getAwardedAt())
                .build();
    }
} 