package com.StudyOps.domain.member.service;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.user.entity.EndUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyMemberService {
    private final StudyMemberRepository studyMemberRepository;

    //StudyMember builder를 활용하여 생성 후 디비에 정보를 저장한다.
    public void createStudyMember(EndUser endUser, StudyGroup studyGroup, boolean isHost){
        StudyMember studyMember = StudyMember.builder()
                .studyGroup(studyGroup)
                .endUser(endUser)
                .hostStatus(true)
                .totalPenalty(0)
                .joinDate(LocalDate.now())
                .build();
        studyMemberRepository.save(studyMember);
    }
}
