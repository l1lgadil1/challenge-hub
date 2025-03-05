package com.challengehub.service;

import com.challengehub.dto.userchallenge.UserChallengeDto;
import com.challengehub.dto.userchallenge.UserChallengeRequest;

import java.util.List;

public interface UserChallengeService {
    List<UserChallengeDto> getUserChallenges();
    List<UserChallengeDto> getUserChallengesByCompleted(Boolean completed);
    UserChallengeDto getUserChallengeById(Long id);
    UserChallengeDto startChallenge(UserChallengeRequest request);
    UserChallengeDto updateUserChallenge(Long id, UserChallengeRequest request);
    void deleteUserChallenge(Long id);
} 