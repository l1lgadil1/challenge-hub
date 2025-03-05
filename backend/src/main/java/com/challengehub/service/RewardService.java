package com.challengehub.service;

import com.challengehub.dto.reward.AwardRewardRequest;
import com.challengehub.dto.reward.RewardRequest;
import com.challengehub.dto.reward.RewardResponse;

import java.util.List;

public interface RewardService {
    List<RewardResponse> getUserRewards();
    RewardResponse getRewardById(Long id);
    RewardResponse createReward(RewardRequest rewardRequest);
    RewardResponse awardReward(AwardRewardRequest awardRewardRequest);
} 