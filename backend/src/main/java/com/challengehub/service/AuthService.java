package com.challengehub.service;

import com.challengehub.dto.auth.AuthResponse;
import com.challengehub.dto.auth.LoginRequest;
import com.challengehub.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
} 