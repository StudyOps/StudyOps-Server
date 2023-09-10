package com.StudyOps.domain.attendance.entity;

import com.StudyOps.domain.member.entity.StudyMember;
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
@Builder
public class StudyAttendanceVote {
    @Id @GeneratedValue
    @Column(name = "study_attendance_vote_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_member_id")
    private StudyMember studyMember;
    private Boolean attendance;
    private LocalDate date;
}