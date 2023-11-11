package com.StudyOps.domain.penalty.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class StudyGroupNotSettledDayDto {
     private List<LocalDate> notSettledDays;
}
