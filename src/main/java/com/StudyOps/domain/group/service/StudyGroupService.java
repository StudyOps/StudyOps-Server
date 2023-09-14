package com.StudyOps.domain.group.service;

import com.StudyOps.domain.group.dto.StudyGroupCreateReqDto;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;


    public void createStudyGroup(Long userId, StudyGroupCreateReqDto studyGroupCreateReqDto) {

    }
}
