package com.StudyOps.domain.penalty.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyGroupLateMemberInfoDto {
    private Long penaltyId;
    private Boolean isSettled;
    private String name;
    private int lateTime;
}
