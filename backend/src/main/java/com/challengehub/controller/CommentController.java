package com.challengehub.controller;

import com.challengehub.dto.comment.CommentRequest;
import com.challengehub.dto.comment.CommentResponse;
import com.challengehub.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Comments", description = "Endpoints for managing comments on challenges")
public class CommentController {

    private final CommentService commentService;

    @Operation(
        summary = "Get comments for a challenge",
        description = "Returns a list of comments for a specific challenge"
    )
    @GetMapping("/challenge/{challengeId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByChallenge(@PathVariable Long challengeId) {
        return ResponseEntity.ok(commentService.getCommentsByChallenge(challengeId));
    }

    @Operation(
        summary = "Add a comment",
        description = "Adds a comment to a challenge"
    )
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.addComment(commentRequest));
    }

    @Operation(
        summary = "Update a comment",
        description = "Updates an existing comment"
    )
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(id, commentRequest));
    }

    @Operation(
        summary = "Delete a comment",
        description = "Deletes an existing comment"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
} 