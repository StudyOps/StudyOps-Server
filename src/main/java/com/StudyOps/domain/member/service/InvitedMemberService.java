package com.StudyOps.domain.member.service;

import com.StudyOps.domain.group.dto.StudyGroupResDto;
import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.dto.InvitedMemberStatus;
import com.StudyOps.domain.member.entity.AcceptStatus;
import com.StudyOps.domain.member.entity.InvitedMember;
import com.StudyOps.domain.member.repository.InvitedMemberRepository;
import com.StudyOps.domain.schedule.dto.StudyScheduleDto;
import com.StudyOps.domain.schedule.repository.StudyScheduleRepository;
import com.StudyOps.domain.user.entity.EndUser;
import com.StudyOps.domain.user.repository.EndUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InvitedMemberService {
    private final InvitedMemberRepository invitedMemberRepository;
    private final EndUserRepository endUserRepository;
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
                    EndUser findEndUser = endUserRepository.findByNickname(nickname).get();
                    InvitedMember invitedMember = InvitedMember.builder()
                            .endUser(findEndUser)
                            .studyGroup(studyGroupRepository.findById(groupId).get())
                            .acceptStatus(AcceptStatus.WAIT)
                            .build();
                    return invitedMember;
                })
                .forEach(invitedMemberRepository::save);
    }

    public void acceptInvitedStudyGroup(Long groupId, Long userId) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        EndUser endUser = endUserRepository.findById(userId).get();
        InvitedMember invitedMember = invitedMemberRepository.findByStudyGroupAndEndUser(studyGroup, endUser).get();
        invitedMember.accept();
        studyGroup.increaseHeadCount();

        studyMemberService.createStudyMember(endUser,studyGroup,false);
    }

    public void rejectInvitedStudyGroup(Long groupId, Long userId) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        EndUser endUser = endUserRepository.findById(userId).get();
        InvitedMember invitedMember = invitedMemberRepository.findByStudyGroupAndEndUser(studyGroup, endUser).get();
        invitedMember.reject();
    }
    public List<StudyGroupResDto> getAllInvitedStudyGroups(Long userId) {

        EndUser endUser = endUserRepository.findById(userId).get();

        List<InvitedMember> invitedMembers = invitedMemberRepository.findAllByEndUser(endUser);
        List<StudyGroupResDto> resDtos = invitedMembers.stream()
                .filter(member -> member.getAcceptStatus() == AcceptStatus.WAIT)
                .map(member -> {
                    StudyGroup studyGroup = member.getStudyGroup();
                    List<StudyScheduleDto> studyScheduleDtos = studyScheduleRepository.findAllByStudyGroup(studyGroup)
                            .stream()
                            .map(schedule -> StudyScheduleDto.builder()
                                    .dayWeek(schedule.getDayWeek())
                                    .startTime(schedule.getStartTime())
                                    .finishTime(schedule.getFinishTime())
                                    .build())
                            .collect(Collectors.toList());

                    return StudyGroupResDto.builder()
                            .groupId(studyGroup.getId())
                            .name(studyGroup.getName())
                            .intro(studyGroup.getIntro())
                            .schedules(studyScheduleDtos)
                            .hostName(studyGroup.getHostName())
                            .hostStatus(false)
                            .headCount(studyGroup.getHeadCount())
                            .absenceCost(studyGroup.getAbsentCost())
                            .lateCost(studyGroup.getLateCost())
                            .startDate(studyGroup.getStartDate())
                            .build();
                })
                .collect(Collectors.toList());

        return resDtos;
    }

    public List<InvitedMemberStatus> getInvitedMemberStatus(Long groupId) {
        List<InvitedMemberStatus> invitedMemberStatusList = new ArrayList<>();

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();

        List<InvitedMember> members = invitedMemberRepository.findAllByStudyGroup(studyGroup);

        for(int i=0; i<members.size(); i++){
            InvitedMember invitedMember = members.get(i);
            invitedMemberStatusList.add(InvitedMemberStatus.builder()
                    .nickName(invitedMember.getEndUser().getNickname())
                    .status(invitedMember.getAcceptStatus())
                    .build());
        }
        return invitedMemberStatusList;
    }
}


