package com.StudyOps.domain.attendance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
public class StudyScheduleAndAttendanceResDto {

    private Boolean isStudyDay;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isAttendant;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isLate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer lateTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime finishTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalDateTime attendanceTime;
}
