package com.StudyOps.domain.user.dto;

import com.StudyOps.domain.user.entity.EndUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EndUserResponseDto {
    private String email;

    public static EndUserResponseDto of(EndUser endUser) {
        return new EndUserResponseDto(endUser.getEmail());
    }
}