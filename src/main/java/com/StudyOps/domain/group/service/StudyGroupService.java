package com.StudyOps.domain.group.service;

import com.StudyOps.domain.group.dto.StudyGroupCreateReqDto;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.user.entity.User;
import com.StudyOps.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;

    //테스트 유저 먼저 생성
    @PostConstruct
    public void init(){
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        user1.setNickname("이찬희");
        user2.setNickname("장희영");
        user3.setNickname("소예원");
        user4.setNickname("누구지");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
    }

    public void createStudyGroup(Long userId, StudyGroupCreateReqDto studyGroupCreateReqDto) {


    }
}
