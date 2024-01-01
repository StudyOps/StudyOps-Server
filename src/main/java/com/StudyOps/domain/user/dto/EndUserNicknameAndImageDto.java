package com.StudyOps.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EndUserNicknameAndImageDto {
    private String nickName;
    private String profileImage;
}
