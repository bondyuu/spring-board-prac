package com.example.boardprac.global;

import lombok.Getter;

public enum PostStatus {
    ACTIVE("활성게시글"),
    BANNED("관리자삭제"),
    DELETED("회원삭제");

    @Getter
    private final String desc;

    PostStatus(String desc) {
        this.desc = desc;
    }
}
