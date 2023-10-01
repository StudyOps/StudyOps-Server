package com.StudyOps.domain.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class StudyAttendanceAndAbsenceDto {

    private List<String> attendMemberList;
    private List<String> absenceMemberList;
    private Boolean isAttended;//해당 날짜의 나의 참석 여부
}
