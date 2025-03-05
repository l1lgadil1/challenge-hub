package com.challengehub.repository;

import com.challengehub.model.Reward;
import com.challengehub.model.User;
import com.challengehub.model.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRewardRepository extends JpaRepository<UserReward, Long> {
    List<UserReward> findByUser(User user);
    List<UserReward> findByUserAndReward(User user, Reward reward);
} 