package com.challengehub.repository;

import com.challengehub.model.Challenge;
import com.challengehub.model.Comment;
import com.challengehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByChallenge(Challenge challenge);
    List<Comment> findByUser(User user);
} 