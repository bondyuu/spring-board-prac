package com.example.boardprac.dto;

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

    @Builder
    public UserDto(long id, String email) {
        this.id = id;
        this.email = email;
    }
}
