package com.StudyOps.domain.penalty.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyGroupAbsentMemberInfoDto {
    private Long penaltyId;
    private Boolean isSettled;
    private String nickName;
    private String profileImageUrl;
}
