package com.example.boardprac.dto;

import com.example.boardprac.domain.RefreshToken;
import com.example.boardprac.global.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private TokenDto token;
    private String role;

    @Builder
    public LoginResponseDto(TokenDto token, Role role) {
        this.token = token;
        this.role = role.getDesc();
    }
}
