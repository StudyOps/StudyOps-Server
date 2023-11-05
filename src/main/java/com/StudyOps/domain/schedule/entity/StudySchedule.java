package com.StudyOps.domain.schedule.entity;

import com.StudyOps.domain.group.entity.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudySchedule {
    @Id
    @GeneratedValue
    @Column(name = "study_schedule_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;
    private String dayWeek;
    private LocalTime startTime;
    private LocalTime finishTime;
}
