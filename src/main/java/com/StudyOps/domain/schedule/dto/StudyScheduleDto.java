package com.StudyOps.domain.schedule.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyScheduleDto {
    private String dayWeek;
    @JsonFormat(pattern = "HH:mm")  // json 데이터 전송시 초단위 생략하기 위한 데이터 직렬화 어노테이션
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime finishTime;
}
