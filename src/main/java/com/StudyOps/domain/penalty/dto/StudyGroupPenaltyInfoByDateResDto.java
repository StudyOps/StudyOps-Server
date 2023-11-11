package com.StudyOps.domain.penalty.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StudyGroupPenaltyInfoByDateResDto {
    private List<StudyGroupAbsentMemberInfoDto> absentMembers;
    private List<StudyGroupLateMemberInfoDto> lateMembers;
    private int lateCost;
    private int absentCost;
}
