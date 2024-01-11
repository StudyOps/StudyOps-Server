package com.StudyOps.domain.user.entity;

import com.StudyOps.domain.user.dto.EndUserInfoReqDto;
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

    public void changePassword(String newPassword){
        this.password = newPassword;
    }
    public String changeEndUserInfo(EndUserInfoReqDto endUserInfoReqDto){
        this.nickname = endUserInfoReqDto.getNickName();
        return this.getNickname();
    }
    public void changeNickName(String newNickName){
        this.nickname =newNickName;
    }
    public void changeProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }

}
