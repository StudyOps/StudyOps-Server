package com.StudyOps.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndUserResponseDto {
    private String email;
    private String nickName;
    private String profileImageUrl;
}
