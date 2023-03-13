package com.example.boardprac.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class AdminUserResponseDto {
    private UserDto user;
    private List<PostDto> postList;
    private List<PostDto> likeList;

    @Builder
    public AdminUserResponseDto(UserDto user, List<PostDto> postList, List<PostDto> likeList) {
        this.user = user;
        this.postList = postList;
        this.likeList = likeList;
    }
}
