package com.challengehub.service.impl;

import com.challengehub.dto.challenge.ChallengeDto;
import com.challengehub.exception.ResourceNotFoundException;
import com.challengehub.model.Challenge;
import com.challengehub.repository.ChallengeRepository;
import com.challengehub.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Override
    public List<ChallengeDto> getAllChallenges() {
        return challengeRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChallengeDto getChallengeById(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", id));
        return mapToDto(challenge);
    }

    @Override
    public List<ChallengeDto> getChallengesByCategory(String category) {
        return challengeRepository.findByCategory(category).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChallengeDto createChallenge(ChallengeDto challengeDto) {
        Challenge challenge = mapToEntity(challengeDto);
        Challenge savedChallenge = challengeRepository.save(challenge);
        return mapToDto(savedChallenge);
    }

    @Override
    public ChallengeDto updateChallenge(Long id, ChallengeDto challengeDto) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", id));
        
        challenge.setTitle(challengeDto.getTitle());
        challenge.setDescription(challengeDto.getDescription());
        challenge.setCategory(challengeDto.getCategory());
        challenge.setDurationDays(challengeDto.getDurationDays());
        
        Challenge updatedChallenge = challengeRepository.save(challenge);
        return mapToDto(updatedChallenge);
    }

    @Override
    public void deleteChallenge(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", id));
        challengeRepository.delete(challenge);
    }
    
    private ChallengeDto mapToDto(Challenge challenge) {
        return ChallengeDto.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .category(challenge.getCategory())
                .durationDays(challenge.getDurationDays())
                .build();
    }
    
    private Challenge mapToEntity(ChallengeDto challengeDto) {
        return Challenge.builder()
                .title(challengeDto.getTitle())
                .description(challengeDto.getDescription())
                .category(challengeDto.getCategory())
                .durationDays(challengeDto.getDurationDays())
                .build();
    }
} 