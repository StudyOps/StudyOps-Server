package com.StudyOps.domain.member.service;

import com.StudyOps.domain.group.dto.StudyGroupResDto;
import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.AcceptStatus;
import com.StudyOps.domain.member.entity.InvitedMember;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.InvitedMemberRepository;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.schedule.dto.StudyScheduleDto;
import com.StudyOps.domain.schedule.entity.StudySchedule;
import com.StudyOps.domain.schedule.repository.StudyScheduleRepository;
import com.StudyOps.domain.user.entity.User;
import com.StudyOps.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InvitedMemberService {
    private final InvitedMemberRepository invitedMemberRepository;
    private final UserRepository userRepository;
    private final StudyScheduleRepository studyScheduleRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyMemberService studyMemberService;

    /**
     예외처리  1. 중복초대 방지
            2. 없는 닉네임 초대 방지
     */
    public void createInvitedMember(Long groupId, List<String> invitees) {
        //스트림과 맵을 활용하여 리스트 invitees에 있는 닉네임을 userRepository에서 조회후 invitedMember로 디비에 등록한다.
        invitees.stream()
                .map(nickname -> {
                    User findUser = userRepository.findByNickname(nickname).get();
                    InvitedMember invitedMember = InvitedMember.builder()
                            .user(findUser)
                            .studyGroup(studyGroupRepository.findById(groupId).get())
                            .acceptStatus(AcceptStatus.WAIT)
                            .build();
                    return invitedMember;
                })
                .forEach(invitedMemberRepository::save);
    }

    public void acceptInvitedStudyGroup(Long groupId, Long userId) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        User user = userRepository.findById(userId).get();
        InvitedMember invitedMember = invitedMemberRepository.findByStudyGroupAndUser(studyGroup, user).get();
        invitedMember.accept();

        studyMemberService.createStudyMember(user,studyGroup,false);
    }

    public void rejectInvitedStudyGroup(Long groupId, Long userId) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        User user = userRepository.findById(userId).get();
        InvitedMember invitedMember = invitedMemberRepository.findByStudyGroupAndUser(studyGroup, user).get();
        invitedMember.reject();
    }
    public List<StudyGroupResDto> getAllInvitedStudyGroups(Long userId) {
        List<StudyGroupResDto> resDtos = new ArrayList<>();

        User user = userRepository.findById(userId).get();

        List<InvitedMember> invitedMembers = invitedMemberRepository.findAllByUser(user);
        for(int i=0; i<invitedMembers.size(); i++){
            if(invitedMembers.get(i).getAcceptStatus()!=AcceptStatus.WAIT)
                continue;
            StudyGroup studyGroup = invitedMembers.get(i).getStudyGroup();
            List<StudySchedule> studySchedules = studyScheduleRepository.findAllByStudyGroup(studyGroup);
            List<StudyScheduleDto> studyScheduleDtos = new ArrayList<>();
            for(int j=0; j<studySchedules.size(); j++){
                StudyScheduleDto studyScheduleDto = StudyScheduleDto.builder()
                        .dayWeek(studySchedules.get(j).getDayWeek())
                        .startTime(studySchedules.get(j).getStartTime())
                        .finishTime(studySchedules.get(j).getFinishTime())
                        .build();
                studyScheduleDtos.add(studyScheduleDto);
            }
            StudyGroupResDto studyGroupResDto = StudyGroupResDto.builder()
                    .groupId(studyGroup.getId())
                    .name(studyGroup.getName())
                    .intro(studyGroup.getIntro())
                    .schedules(studyScheduleDtos)
                    .hostName(studyGroup.getHostName())
                    .hostStatus(false)
                    .headCount(studyGroup.getHeadCount())
                    .absenceCost(studyGroup.getAbsenceCost())
                    .lateCost(studyGroup.getLateCost())
                    .startDate(studyGroup.getStartDate())
                    .build();
            resDtos.add(studyGroupResDto);
        }
        return resDtos;
    }
}

