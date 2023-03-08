package com.example.boardprac.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class PostDto {
    private long id;
    private String title;
    private String content;
    private UserDto user;
    private long heartNum;

    @Builder
    public PostDto(long id, String title, String content, UserDto user, long heartNum) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.heartNum = heartNum;
    }
}
