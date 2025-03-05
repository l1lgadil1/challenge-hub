package com.challengehub.dto.auth;

import com.challengehub.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String refreshToken;
    private UserDto user;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDto {
        private Long id;
        private String name;
        private String email;
        private String role;
        
        public static UserDto fromUser(User user) {
            return UserDto.builder()
                    .id(user.getId())
                    .name(user.getUsernameField())
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .build();
        }
    }
} 