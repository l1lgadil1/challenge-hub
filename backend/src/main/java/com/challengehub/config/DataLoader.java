package com.challengehub.config;

import com.challengehub.model.*;
import com.challengehub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final PasswordEncoder passwordEncoder;

    @Bean
    @Profile("!prod")
    public CommandLineRunner initData(
            UserRepository userRepository, 
            ChallengeRepository challengeRepository,
            UserChallengeRepository userChallengeRepository,
            RewardRepository rewardRepository,
            UserRewardRepository userRewardRepository,
            CommentRepository commentRepository) {
        return args -> {
            // Create admin user
            if (userRepository.count() == 0) {
                User admin = User.builder()
                        .username("admin")
                        .email("admin@example.com")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.ADMIN)
                        .build();
                
                User user = User.builder()
                        .username("user")
                        .email("user@example.com")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.USER)
                        .build();
                
                userRepository.saveAll(List.of(admin, user));
            }
            
            // Create sample challenges
            if (challengeRepository.count() == 0) {
                Challenge challenge1 = Challenge.builder()
                        .title("30 Days of Meditation")
                        .description("Meditate for at least 10 minutes every day for 30 days.")
                        .category("Meditation")
                        .durationDays(30)
                        .build();
                
                Challenge challenge2 = Challenge.builder()
                        .title("Read 5 Books in a Month")
                        .description("Challenge yourself to read 5 books in a single month.")
                        .category("Reading")
                        .durationDays(30)
                        .build();
                
                Challenge challenge3 = Challenge.builder()
                        .title("Learn a New Programming Language")
                        .description("Learn the basics of a new programming language in 21 days.")
                        .category("Learning")
                        .durationDays(21)
                        .build();
                
                Challenge challenge4 = Challenge.builder()
                        .title("Daily Exercise Challenge")
                        .description("Exercise for at least 30 minutes every day for 30 days.")
                        .category("Fitness")
                        .durationDays(30)
                        .build();
                
                Challenge challenge5 = Challenge.builder()
                        .title("No Social Media for a Week")
                        .description("Stay off all social media platforms for 7 days.")
                        .category("Digital Detox")
                        .durationDays(7)
                        .build();
                
                List<Challenge> challenges = challengeRepository.saveAll(List.of(
                        challenge1, challenge2, challenge3, challenge4, challenge5
                ));
                
                // Create sample user challenges
                User user = userRepository.findByEmail("user@example.com").orElseThrow();
                
                UserChallenge userChallenge1 = UserChallenge.builder()
                        .user(user)
                        .challenge(challenge1)
                        .progress(10)
                        .completed(false)
                        .build();
                
                UserChallenge userChallenge2 = UserChallenge.builder()
                        .user(user)
                        .challenge(challenge3)
                        .progress(100)
                        .completed(true)
                        .build();
                
                userChallengeRepository.saveAll(List.of(userChallenge1, userChallenge2));
                
                // Create sample rewards
                Reward reward1 = Reward.builder()
                        .name("First Challenge Completed")
                        .description("Completed your first challenge")
                        .type("BADGE")
                        .build();
                
                Reward reward2 = Reward.builder()
                        .name("Learning Master")
                        .description("Completed a learning challenge")
                        .type("BADGE")
                        .build();
                
                Reward reward3 = Reward.builder()
                        .name("Challenge Points")
                        .description("Points earned for completing challenges")
                        .type("POINTS")
                        .pointsValue(100)
                        .build();
                
                List<Reward> rewards = rewardRepository.saveAll(List.of(reward1, reward2, reward3));
                
                // Award rewards to user
                UserReward userReward1 = UserReward.builder()
                        .user(user)
                        .reward(reward1)
                        .build();
                
                UserReward userReward2 = UserReward.builder()
                        .user(user)
                        .reward(reward2)
                        .build();
                
                userRewardRepository.saveAll(List.of(userReward1, userReward2));
                
                // Create sample comments
                Comment comment1 = Comment.builder()
                        .user(user)
                        .challenge(challenge1)
                        .content("This challenge is really helping me focus better!")
                        .createdAt(LocalDateTime.now().minusDays(5))
                        .build();
                
                Comment comment2 = Comment.builder()
                        .user(user)
                        .challenge(challenge3)
                        .content("I'm learning Python and it's amazing!")
                        .createdAt(LocalDateTime.now().minusDays(2))
                        .build();
                
                commentRepository.saveAll(List.of(comment1, comment2));
                challengeRepository.saveAll(List.of(challenge1, challenge2, challenge3, challenge4, challenge5));
            }
        };
    }
} 