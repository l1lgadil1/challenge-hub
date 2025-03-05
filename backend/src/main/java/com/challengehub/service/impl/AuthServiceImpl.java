package com.challengehub.service.impl;

import com.challengehub.dto.auth.AuthResponse;
import com.challengehub.dto.auth.LoginRequest;
import com.challengehub.dto.auth.RegisterRequest;
import com.challengehub.exception.ResourceNotFoundException;
import com.challengehub.model.Role;
import com.challengehub.model.User;
import com.challengehub.repository.UserRepository;
import com.challengehub.security.JwtService;
import com.challengehub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Check if email or username already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }
        
        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        
        userRepository.save(user);
        
        // Generate tokens
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        
        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .user(AuthResponse.UserDto.fromUser(user))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        // Get user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));
        
        // Generate tokens
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        
        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .user(AuthResponse.UserDto.fromUser(user))
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken);
        
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));
            
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                
                return AuthResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .user(AuthResponse.UserDto.fromUser(user))
                        .build();
            }
        }
        
        throw new IllegalArgumentException("Invalid refresh token");
    }
} 