package com.StudyOps.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyScheduleListDto {

    private List<StudyScheduleDto> schedules;
    private LocalDate startDate;
}
