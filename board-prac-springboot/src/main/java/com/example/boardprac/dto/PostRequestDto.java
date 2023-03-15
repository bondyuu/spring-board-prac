package com.example.boardprac.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class PostRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
