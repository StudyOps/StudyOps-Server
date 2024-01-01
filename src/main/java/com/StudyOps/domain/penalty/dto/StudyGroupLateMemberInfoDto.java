package com.StudyOps.domain.penalty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StudyGroupLateMemberInfoDto {
    private Long penaltyId;
    private Boolean isSettled;
    private String nickName;
    private String profileImageUrl;
    private int lateTime;
}
