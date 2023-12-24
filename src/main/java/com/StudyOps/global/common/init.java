package com.StudyOps.global.common;

import com.StudyOps.domain.user.entity.EndUser;
import com.StudyOps.domain.user.repository.EndUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class init {
    private final EndUserRepository endUserRepository;

    //테스트 유저 먼저 생성
    @PostConstruct
    public void init() {
        EndUser endUser1 = new EndUser();
        EndUser endUser2 = new EndUser();
        EndUser endUser3 = new EndUser();
        EndUser endUser4 = new EndUser();

        endUser1.setNickname("이찬희");
        endUser2.setNickname("장희영");
        endUser3.setNickname("소예원");
        endUser4.setNickname("누구지");

        endUserRepository.save(endUser1);
        endUserRepository.save(endUser2);
        endUserRepository.save(endUser3);
        endUserRepository.save(endUser4);
    }
}
