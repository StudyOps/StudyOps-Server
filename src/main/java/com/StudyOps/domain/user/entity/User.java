package com.StudyOps.domain.user.entity;

import com.StudyOps.domain.user.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Getter
//추후 setter 삭제
@Setter
public class User {
    @Id @GeneratedValue
    @JoinColumn(name = "user_id")
    private Long id;
    private String nickname;
}
