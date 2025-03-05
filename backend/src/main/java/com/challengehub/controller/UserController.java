package com.challengehub.controller;

import com.challengehub.dto.user.ChangePasswordRequest;
import com.challengehub.dto.user.UpdateProfileRequest;
import com.challengehub.dto.user.UserDto;
import com.challengehub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = "Endpoints for user profile management")
public class UserController {

    private final UserService userService;

    @Operation(
        summary = "Get current user profile",
        description = "Returns the profile of the authenticated user"
    )
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @Operation(
        summary = "Get user by ID",
        description = "Returns the profile of a specific user"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
        summary = "Update user profile",
        description = "Updates the profile of the authenticated user"
    )
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> updateProfile(@Valid @RequestBody UpdateProfileRequest updateProfileRequest) {
        return ResponseEntity.ok(userService.updateProfile(updateProfileRequest));
    }

    @Operation(
        summary = "Change password",
        description = "Changes the password of the authenticated user"
    )
    @PutMapping("/me/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.ok().build();
    }
} 