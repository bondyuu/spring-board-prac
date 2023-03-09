package com.example.boardprac.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminMainResponseDto {
    private long userNum;
    private long postNum;

    @Builder
    public AdminMainResponseDto(long userNum, long postNum) {
        this.userNum = userNum;
        this.postNum = postNum;
    }
}
