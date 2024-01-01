package com.StudyOps.domain.attendance.dto;

import com.StudyOps.domain.user.dto.EndUserNicknameAndImageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class StudyAttendanceAndAbsenceDto {

    private List<EndUserNicknameAndImageDto> attendMemberList;
    private List<EndUserNicknameAndImageDto> absenceMemberList;
    private Boolean isAttended;//해당 날짜의 나의 참석 여부
}
