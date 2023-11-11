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
@Builder
public class StudyGroup {
    @Id @GeneratedValue
    @Column(name = "study_group_id")
    private Long id;
    private String name;
    private String intro;
    private String rule;
    private int absentCost;
    private int lateCost;
    private int totalCost;
    private int allowedTime;
    private String account;
    private int headCount;
    private String hostName;
    private LocalDate startDate;
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
}
