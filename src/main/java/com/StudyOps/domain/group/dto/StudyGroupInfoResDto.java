package com.StudyOps.domain.group.dto;

import com.StudyOps.domain.schedule.dto.StudyScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class StudyGroupInfoResDto {
    private String name;
    private String intro;
    private String hostName;
    private List<String> members;
    private LocalDate startDate;
    private String rule;
    private List<StudyScheduleDto> schedules;
    private int allowedTime;
    private int lateCost;
    private int absenceCost;
}
