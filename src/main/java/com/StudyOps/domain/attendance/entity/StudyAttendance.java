package com.StudyOps.domain.attendance.entity;

import com.StudyOps.domain.member.entity.StudyMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyAttendance {
    @Id @GeneratedValue
    @Column(name = "study_attendance_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_member_id")
    private StudyMember studyMember;
    private Boolean isLate;
    private int lateTime;
    private LocalDateTime time;
    private LocalDate date;
}
