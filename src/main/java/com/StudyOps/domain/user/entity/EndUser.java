package com.StudyOps.domain.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Getter
//추후 setter 삭제
@Setter
public class EndUser {
    @Id @GeneratedValue
    @JoinColumn(name = "user_id")
    private Long id;
    private String nickname;
}
