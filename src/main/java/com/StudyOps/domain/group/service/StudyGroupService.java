package com.StudyOps.domain.group.service;

import com.StudyOps.domain.group.dto.StudyGroupCreateReqDto;
import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.AcceptStatus;
import com.StudyOps.domain.member.entity.InvitedMember;
import com.StudyOps.domain.member.repository.InvitedMemberRepository;
import com.StudyOps.domain.member.service.InvitedMemberService;
import com.StudyOps.domain.member.service.StudyMemberService;
import com.StudyOps.domain.schedule.entity.StudySchedule;
import com.StudyOps.domain.schedule.repository.StudyScheduleRepository;
import com.StudyOps.domain.schedule.service.StudyScheduleService;
import com.StudyOps.domain.user.entity.User;
import com.StudyOps.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;
    private final StudyMemberService studyMemberService;
    private final StudyScheduleService studyScheduleService;
    private final InvitedMemberService invitedMemberService;

    //테스트 유저 먼저 생성
    @PostConstruct
    public void init() {
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
        //userId로 유저를 찾는다. Optional로 조회되므로 .get()매서드를 사용해준다.
        User user = userRepository.findById(userId).get();

        //studyGroupCreateReqDto를 엔티티로 변환 후 디비에 정보를 저장한다.
        StudyGroup studyGroup = studyGroupCreateReqDto.toEntity();
        studyGroupRepository.save(studyGroup);

        //StudyMember 생성
        studyMemberService.createStudyMember(user, studyGroup, true);

        //StudySchedule 생성
        studyScheduleService.createStudySchedule(studyGroup, studyGroupCreateReqDto.getSchedules());

        //InvitedMember 생성
        invitedMemberService.createInvitedMember(studyGroup, studyGroupCreateReqDto.getInvitees());
    }
}

