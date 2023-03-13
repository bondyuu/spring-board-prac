package com.example.boardprac.dto;

import com.example.boardprac.global.Role;
import com.example.boardprac.global.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private long id;
    private String email;
    private String name;
    private String role;
    private String status;

    @Builder
    public UserDto(long id, String email, String name, Role role, UserStatus status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role.getDesc();
        this.status = status.getDesc();
    }
}
