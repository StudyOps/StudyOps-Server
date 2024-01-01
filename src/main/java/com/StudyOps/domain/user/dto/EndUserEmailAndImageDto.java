package com.StudyOps.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EndUserEmailAndImageDto {
    private String email;
    private String profileImageUrl;
}