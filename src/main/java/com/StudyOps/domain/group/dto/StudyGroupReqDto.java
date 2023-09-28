package com.StudyOps.domain.group.dto;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.schedule.dto.StudyScheduleDto;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class StudyGroupReqDto {

    private String name;
    private String intro;
    private String rule;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private int absenceCost;
    private int lateCost;
    private int allowedTime;
    private String hostName;
    private List<StudyScheduleDto> schedules;
    private List<String> invitees;

    public StudyGroup toEntity(){
        return StudyGroup.builder()
                .name(name)
                .intro(intro)
                .rule(rule)
                .startDate(startDate)
                .absenceCost(absenceCost)
                .lateCost(lateCost)
                .allowedTime(allowedTime)
                .totalCost(0)
                .headCount(1)
                .hostName(hostName)
                .build();
    }
}
