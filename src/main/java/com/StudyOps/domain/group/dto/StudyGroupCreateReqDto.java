package com.StudyOps.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class StudyGroupCreateReqDto {

    private String name;
    private String intro;
    private Map<String, List<String>> time = new HashMap<>();
    private String rule;
    private LocalDate startDate;
    private int lateCost;
    private int absenceCost;
    private int allowedTime;
}
