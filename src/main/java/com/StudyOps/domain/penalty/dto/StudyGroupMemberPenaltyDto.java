package com.StudyOps.domain.penalty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class StudyGroupMemberPenaltyDto {
    private String nickName;
    private String profileImageURl;
    private int penalty;
}
