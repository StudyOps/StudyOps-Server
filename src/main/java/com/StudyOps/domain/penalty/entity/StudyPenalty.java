package com.StudyOps.domain.penalty.entity;

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
public class StudyPenalty {
    @Id @GeneratedValue
    @Column(name = "study_penalty_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_member_id")
    private StudyMember studyMember;
    private int fine;
    private Boolean isPaid;
    private int lateTime;
    private LocalDate date;
    /******************************************
     지각인지 불참인지 여부, 지각 => ture 불참 => false
     ******************************************/
    private Boolean lateAbsent;
}
