package com.example.boardprac.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
