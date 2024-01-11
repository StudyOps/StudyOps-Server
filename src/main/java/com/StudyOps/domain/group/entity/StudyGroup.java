package com.StudyOps.domain.group.entity;

import com.StudyOps.domain.member.entity.InvitedMember;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.schedule.entity.StudySchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyGroup {
    @Id @GeneratedValue
    @Column(name = "study_group_id")
    private Long id;
    private String name;
    private String intro;

    @Column(columnDefinition = "LONGTEXT")
    private String rule;
    private int absentCost;
    private int lateCost;
    private int totalCost;
    private int allowedTime;
    private String account;
    private int headCount;
    private String hostName;
    private LocalDate startDate;
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.REMOVE)
    private List<StudyMember> studyMembers;

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.REMOVE)
    private List<InvitedMember> invitedMembers;

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.REMOVE)
    private List<StudySchedule> studySchedules;
    public void decreaseHeadCount(){
        headCount--;
    }

    public void increaseHeadCount() {
        headCount++;
    }

    public void changeRule(String rule) {
        this.rule = rule;
    }
    public void changeIntro(String intro) {
        this.intro = intro;
    }
    public void changeAccount(String account) {
        this.account = account;
    }
    public void plusTotalCost(int cost){
        this.totalCost += cost;
    }
    public void minusTotalCost(int cost){
        this.totalCost -= cost;
    }
    public void changeHostName(String newNickname){
        this.hostName = newNickname;
    }
}
