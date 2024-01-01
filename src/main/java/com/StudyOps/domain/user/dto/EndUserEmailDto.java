package com.StudyOps.domain.user.dto;

import com.StudyOps.domain.user.entity.EndUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndUserEmailDto {
    private String email;
    public static EndUserEmailDto of (EndUser endUser){
        return new EndUserEmailDto(endUser.getEmail());
    }
}
