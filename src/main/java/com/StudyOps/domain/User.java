package com.StudyOps.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Getter
public class User {
    @Id @GeneratedValue
    @JoinColumn(name = "user_id")
    private Long id;
    private String nickname;
}
