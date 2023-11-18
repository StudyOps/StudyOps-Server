package com.StudyOps.domain.penalty.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StudyGroupPenaltyCountsDto {
    private String nickName;
    private Long absentCount;
    private Long lateCount;
}
