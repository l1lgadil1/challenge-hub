package com.challengehub.service.impl;

import com.challengehub.dto.comment.CommentRequest;
import com.challengehub.dto.comment.CommentResponse;
import com.challengehub.exception.ResourceNotFoundException;
import com.challengehub.model.Challenge;
import com.challengehub.model.Comment;
import com.challengehub.model.User;
import com.challengehub.repository.ChallengeRepository;
import com.challengehub.repository.CommentRepository;
import com.challengehub.repository.UserRepository;
import com.challengehub.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentResponse> getCommentsByChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", challengeId));
        
        return commentRepository.findByChallenge(challenge).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentResponse addComment(CommentRequest commentRequest) {
        User currentUser = getCurrentUser();
        Challenge challenge = challengeRepository.findById(commentRequest.getChallengeId())
                .orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", commentRequest.getChallengeId()));
        
        Comment comment = Comment.builder()
                .user(currentUser)
                .challenge(challenge)
                .content(commentRequest.getContent())
                .build();
        
        Comment savedComment = commentRepository.save(comment);
        return mapToResponse(savedComment);
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long id, CommentRequest commentRequest) {
        User currentUser = getCurrentUser();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        
        // Check if the comment belongs to the current user
        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You don't have permission to update this comment");
        }
        
        // Update the comment content
        comment.setContent(commentRequest.getContent());
        
        Comment updatedComment = commentRepository.save(comment);
        return mapToResponse(updatedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        User currentUser = getCurrentUser();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        
        // Check if the comment belongs to the current user or if the user is an admin
        if (!comment.getUser().getId().equals(currentUser.getId()) && 
                !currentUser.getRole().name().equals("ADMIN")) {
            throw new AccessDeniedException("You don't have permission to delete this comment");
        }
        
        commentRepository.delete(comment);
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
    
    private CommentResponse mapToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .challengeId(comment.getChallenge().getId())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
} 