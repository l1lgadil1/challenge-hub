package com.challengehub.service;

import com.challengehub.dto.comment.CommentRequest;
import com.challengehub.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getCommentsByChallenge(Long challengeId);
    CommentResponse addComment(CommentRequest commentRequest);
    CommentResponse updateComment(Long id, CommentRequest commentRequest);
    void deleteComment(Long id);
} 