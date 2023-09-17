package com.StudyOps.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyScheduleDto {
    private String dayWeek;
    private String startTime;
    private String finishTime;
}
