package com.StudyOps.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyScheduleDto {
    private String dayWeek;
    private String startTime;
    private String finishTime;
}
