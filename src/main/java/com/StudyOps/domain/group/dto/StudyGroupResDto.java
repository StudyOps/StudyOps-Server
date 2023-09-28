package com.StudyOps.domain.group.dto;

import com.StudyOps.domain.schedule.dto.StudyScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class StudyGroupResDto {
    private Long groupId;
    private String name;
    private String intro;
    private List<StudyScheduleDto> schedules;
    private String hostName;
    private boolean hostStatus;
    private int headCount;
    private int absenceCost;
    private int lateCost;
    private LocalDate startDate;
}
