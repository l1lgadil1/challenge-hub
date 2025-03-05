package com.challengehub.service;

import com.challengehub.dto.challenge.ChallengeDto;

import java.util.List;

public interface ChallengeService {
    List<ChallengeDto> getAllChallenges();
    ChallengeDto getChallengeById(Long id);
    List<ChallengeDto> getChallengesByCategory(String category);
    ChallengeDto createChallenge(ChallengeDto challengeDto);
    ChallengeDto updateChallenge(Long id, ChallengeDto challengeDto);
    void deleteChallenge(Long id);
} 