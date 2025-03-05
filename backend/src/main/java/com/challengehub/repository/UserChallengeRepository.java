package com.challengehub.repository;

import com.challengehub.model.Challenge;
import com.challengehub.model.User;
import com.challengehub.model.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {
    List<UserChallenge> findByUser(User user);
    List<UserChallenge> findByUserAndCompleted(User user, Boolean completed);
    Optional<UserChallenge> findByUserAndChallenge(User user, Challenge challenge);
} 