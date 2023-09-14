package com.StudyOps.domain.member.entity;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMember {
    @Id @GeneratedValue
    @Column(name = "study_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;
    private Boolean hostStatus;
    private int totalPenalty;
}
