package com.StudyOps.domain.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
//추후 setter 삭제
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndUser {
    @Id @GeneratedValue
    @JoinColumn(name = "user_id")
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private Authority authority;

}
