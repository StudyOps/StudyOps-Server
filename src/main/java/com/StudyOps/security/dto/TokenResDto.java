package com.StudyOps.security.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResDto {

    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
}