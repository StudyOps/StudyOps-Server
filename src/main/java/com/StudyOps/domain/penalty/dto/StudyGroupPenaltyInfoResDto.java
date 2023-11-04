package com.StudyOps.domain.penalty.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class StudyGroupPenaltyInfoResDto {
    private int totalFine;
    private String account;
    private List<StudyGroupMemberPenaltyDto> settledPenalties;
    private List<StudyGroupMemberPenaltyDto> notSettledPenalties;
}
