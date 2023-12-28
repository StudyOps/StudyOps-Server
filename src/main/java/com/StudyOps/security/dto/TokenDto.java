package com.StudyOps.security.dto;

import com.StudyOps.security.entity.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private TokenResDto tokenResDto;
    private RefreshToken refreshToken;
}
