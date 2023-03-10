package com.example.boardprac.global;

import lombok.Getter;

public enum UserStatus {

    ACTIVE("활성회원"),
    BANNED("추방회원"),
    LEAVED("탈퇴회원");

    @Getter
    private final String desc;

    UserStatus(String desc) {
        this.desc = desc;
    }
}
