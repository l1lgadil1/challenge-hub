package com.challengehub.service;

import com.challengehub.dto.user.ChangePasswordRequest;
import com.challengehub.dto.user.UpdateProfileRequest;
import com.challengehub.dto.user.UserDto;

public interface UserService {
    UserDto getCurrentUser();
    UserDto getUserById(Long id);
    UserDto updateProfile(UpdateProfileRequest request);
    void changePassword(ChangePasswordRequest request);
} 