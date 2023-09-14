package com.StudyOps.domain.group.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyGroup {
    @Id @GeneratedValue
    @Column(name = "study_group_id")
    private Long id;
    private String name;
    private String rule;
    private int absentPenalty;
    private int latePenalty;
    private int totalPenalty;
    private String account;
    private int headCount;
    private LocalDate startDate;
    private String hostName;
    private int allowedTime;
    private String intro;
}
