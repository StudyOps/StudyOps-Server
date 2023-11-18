package com.StudyOps.domain.penalty.service;

import com.StudyOps.domain.attendance.repository.StudyAttendanceRepository;
import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.penalty.dto.*;
import com.StudyOps.domain.penalty.entity.StudyAbsentPenalty;
import com.StudyOps.domain.penalty.entity.StudyLatePenalty;
import com.StudyOps.domain.penalty.entity.StudyPenalty;
import com.StudyOps.domain.penalty.repository.StudyPenaltyRepository;
import com.StudyOps.domain.schedule.entity.StudySchedule;
import com.StudyOps.domain.schedule.repository.StudyScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyPenaltyService {
    private final StudyPenaltyRepository studyPenaltyRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyScheduleRepository studyScheduleRepository;
    private final StudyAttendanceRepository studyAttendanceRepository;

    //매 정각마다 불참자 정보 업데이트
    /**
        1. 스터디 그룹 시작날짜가 현재 시간 이후인것만 전체 조회
        2. 전체 조회한 스터디그룹에서 for문 돌면서 오늘 요일 및 끝나는 시간 현재 정각이하 1시간 전 초과인것만 뽑기
        3. 해당 스터디 그룹 스터디 멤버 전체 조회
        4. 멤버별로 오늘 해당 되는 날짜 출결 테이블에 없으면 불참 처리
     **/
    @Scheduled(cron = "0 * * * * ?") //"0 0 0/1 * * *"
    public void updateAbsentStudyMember() {
        LocalTime now = LocalTime.now();
        LocalDate target;
        if(now.compareTo(LocalTime.of(0,0)) == 0) // 00시 일경우
            target = LocalDate.now().minusDays(1);
        else
            target = LocalDate.now();
        updateAbsentMemberLogic(target);
    }
    private void updateAbsentMemberLogic(LocalDate target){
        //스터디 그룹 시작날짜가 현재 시간 이전인것만 전체 조회한다.
        List<StudyGroup> allGroups = studyGroupRepository.findAllByStartDateIsLessThanEqual(target);

        LocalTime now = LocalTime.now();
        for (int i = 0; i < allGroups.size(); i++) {
            StudyGroup studyGroup = allGroups.get(i);
            String dayOfWeek = target.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

            Optional<StudySchedule> studySchedule;
            if(now.compareTo(LocalTime.of(0,0)) == 0) {
                studySchedule = studyScheduleRepository.findByStudyGroupAndDayWeekAndFinishTimeGreaterThanAndFinishTimeLessThanEqualOrFinishTimeEquals(studyGroup, dayOfWeek, now.minusHours(1), now.minusMinutes(1), LocalTime.of(0, 0));
            }
            else{
                studySchedule = studyScheduleRepository.findByStudyGroupAndDayWeekAndFinishTimeGreaterThanAndFinishTimeLessThanEqual(studyGroup,dayOfWeek,now.minusHours(1),now);
            }
            if(studySchedule.isEmpty())
                continue;
            List<StudyMember> members = studyMemberRepository.findAllByStudyGroup(studyGroup);
            for(int j=0; j<members.size(); j++){
                StudyMember studyMember = members.get(j);
                if(studyPenaltyRepository.findByStudyMemberAndDate(studyMember,target).isPresent())
                    continue;
                if(studyAttendanceRepository.findByStudyMemberAndDate(studyMember,target).isEmpty());
                {
                    StudyAbsentPenalty studyAbsentPenalty = StudyAbsentPenalty.builder()
                            .studyMember(studyMember)
                            .studyGroup(studyGroup)
                            .fine(studyGroup.getAbsentCost())
                            .isSettled(false)
                            .isExempted(false)
                            .date(target)
                            .build();
                    studyPenaltyRepository.save(studyAbsentPenalty);
                }
            }
        }
    }

    public void deleteLateStudyMember(StudyMember studyMember){
        List<StudyLatePenalty> penalties = studyPenaltyRepository.findLatePenaltiesByStudyMember(studyMember);
        penalties.stream()
                .forEach(penalty -> studyPenaltyRepository.delete(penalty));
    }

    public void deleteAbsentStudyMember(StudyMember studyMember){
        List<StudyAbsentPenalty> penalties = studyPenaltyRepository.findAbsentPenaltiesByStudyMember(studyMember);
        penalties.stream()
                .forEach(penalty -> studyPenaltyRepository.delete(penalty));
    }

    public StudyGroupPenaltyInfoResDto getStudyPenaltyInfo(Long groupId) {
        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();

        //스터디 그룹 pk로 해당 스터디 전체 멤버 조회한다.
        List<StudyMember> members = studyMemberRepository.findAllByStudyGroup(studyGroup);

        List<StudyGroupMemberPenaltyDto> settledPenaltyDtos = new ArrayList<>();
        List<StudyGroupMemberPenaltyDto> notSettledPenaltyDtos = new ArrayList<>();

        for(int i=0; i<members.size(); i++){
            StudyMember studyMember = members.get(i);

            //조회한 스터디멤버 해당 dto리스트에 삽입한다.
            settledPenaltyDtos.add(StudyGroupMemberPenaltyDto.builder()
                    .name(studyMember.getUser().getNickname())
                    .penalty(studyMember.getTotalPenalty())
                    .build());

            //스터디 멤버와 정산여부 필드 false값으로 studyPenalty조회한다.
            List<StudyLatePenalty> notSettledLatePenalties = studyPenaltyRepository.findLatePenaltiesByStudyMemberAndIsSettledAndIsExempted(studyMember,false,false);
            List<StudyAbsentPenalty> notSettledAbsentPenalties = studyPenaltyRepository.findAbsentPenaltiesByStudyMemberAndIsSettledAndIsExempted(studyMember, false,false);
            int penaltySum=0;
            // 각 penalty정보에 대하여 해당 멤버의 정산되지 않은 누적 벌금을 구한다.
            for(int j=0; j<notSettledLatePenalties.size(); j++){
                StudyLatePenalty notSettledPenalty = notSettledLatePenalties.get(j);
                penaltySum += notSettledPenalty.getFine();
            }
            for(int j=0; j<notSettledAbsentPenalties.size(); j++){
                StudyAbsentPenalty notSettledPenalty = notSettledAbsentPenalties.get(j);
                penaltySum += notSettledPenalty.getFine();
            }
            // 정산되지 않은 금액이 0이 아닐경우에만 dto리스트에 추가한다.
            if(penaltySum != 0)
                notSettledPenaltyDtos.add(StudyGroupMemberPenaltyDto.builder()
                    .name(studyMember.getUser().getNickname())
                    .penalty(penaltySum)
                    .build());
        }

        return StudyGroupPenaltyInfoResDto.builder()
                .totalFine(studyGroup.getTotalCost())
                .account(studyGroup.getAccount())
                .settledPenalties(settledPenaltyDtos)
                .notSettledPenalties(notSettledPenaltyDtos)
                .build();
    }

    public StudyGroupNotSettledDayDto getNotSettledDay(Long groupId) {
        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        List<LocalDate> notSettledDays = new ArrayList<>();

        for(LocalDate start = studyGroup.getStartDate(); start.isBefore(LocalDate.now()) || start.equals(LocalDate.now()); start = start.plusDays(1)){
            if(!(studyPenaltyRepository.findAllByStudyGroupAndDateAndIsSettledAndIsExempted(studyGroup,start,false,false).isEmpty()))
                notSettledDays.add(start);
        }
        return StudyGroupNotSettledDayDto.builder()
                .notSettledDays(notSettledDays)
                .build();
    }

    public StudyGroupPenaltyInfoByDateResDto getPenaltyInfoByDate(Long groupId, LocalDate date) {

        List<StudyGroupLateMemberInfoDto> lateMembers = new ArrayList<>();
        List<StudyGroupAbsentMemberInfoDto> absentMembers = new ArrayList<>();

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        List<StudyLatePenalty> studyLatePenalties = studyPenaltyRepository.findAllLatePenaltyByStudyGroupAndDateAndIsExempted(studyGroup, date,false);
        List<StudyAbsentPenalty> studyAbsentPenalties = studyPenaltyRepository.findAllAbsentPenaltyByStudyGroupAndDateAndIsExempted(studyGroup, date,false);

        for(int i=0; i<studyLatePenalties.size(); i++){
            StudyLatePenalty studyLatePenalty = studyLatePenalties.get(i);
            StudyMember studyMember = studyLatePenalty.getStudyMember();

            lateMembers.add(StudyGroupLateMemberInfoDto.builder()
                    .penaltyId(studyLatePenalty.getId())
                    .name(studyMember.getUser().getNickname())
                    .isSettled(studyLatePenalty.getIsSettled())
                    .lateTime(studyLatePenalty.getLateTime())
                    .build());
        }
        for(int i=0; i<studyAbsentPenalties.size(); i++){
            StudyAbsentPenalty studyAbsentPenalty = studyAbsentPenalties.get(i);
            StudyMember studyMember = studyAbsentPenalty.getStudyMember();

            absentMembers.add(StudyGroupAbsentMemberInfoDto.builder()
                    .penaltyId(studyAbsentPenalty.getId())
                    .name(studyMember.getUser().getNickname())
                    .isSettled(studyAbsentPenalty.getIsSettled())
                    .build());
        }
       return StudyGroupPenaltyInfoByDateResDto.builder()
               .absentMembers(absentMembers)
               .lateMembers(lateMembers)
               .lateCost(studyGroup.getLateCost())
               .absentCost(studyGroup.getAbsentCost())
               .build();
    }

    public void settleStudyGroupPenalty(Long penaltyId) {

        StudyPenalty studyPenalty = studyPenaltyRepository.findById(penaltyId).get();
        StudyMember studyMember = studyPenalty.getStudyMember();
        StudyGroup studyGroup = studyPenalty.getStudyGroup();

        studyPenalty.changeToSettled();
        studyMember.plusTotalPenalty(studyPenalty.getFine());
        studyGroup.plusTotalCost(studyPenalty.getFine());
    }

    public StudyGroupPenaltyInfoByDateResDto getPenaltyInfoByBetweenDate(Long groupId, LocalDate start, LocalDate finish) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        List<StudyGroupAbsentMemberInfoDto> absentMembers = new ArrayList<>();
        List<StudyGroupLateMemberInfoDto> lateMembers =  new ArrayList<>();

            List<StudyAbsentPenalty> absentPenalties = studyPenaltyRepository.findAllAbsentPenaltiesByStudyGroupAndDateBetweenAndIsSettledAndIsExempted(studyGroup, start, finish, false,false);
            List<StudyLatePenalty> latePenalties = studyPenaltyRepository.findAllLatePenaltiesByStudyGroupAndDateBetweenAndIsSettledAndIsExempted(studyGroup, start, finish, false, false);

            for(int i=0; i<absentPenalties.size(); i++){

                StudyAbsentPenalty studyAbsentPenalty = absentPenalties.get(i);
                absentMembers.add(StudyGroupAbsentMemberInfoDto.builder()
                        .penaltyId(studyAbsentPenalty.getId())
                        .isSettled(false)
                        .name(studyAbsentPenalty.getStudyMember().getUser().getNickname())
                        .build());
            }
            for(int i=0; i<latePenalties.size(); i++){

                StudyLatePenalty studyLatePenalty = latePenalties.get(i);
                lateMembers.add(StudyGroupLateMemberInfoDto.builder()
                        .penaltyId(studyLatePenalty.getId())
                        .isSettled(false)
                        .name(studyLatePenalty.getStudyMember().getUser().getNickname())
                        .build());
            }

        return StudyGroupPenaltyInfoByDateResDto.builder()
                .absentCost(studyGroup.getAbsentCost())
                .lateCost(studyGroup.getLateCost())
                .lateMembers(lateMembers)
                .absentMembers(absentMembers)
                .build();
    }

    public void settleStudyGroupPenalties(StudyPenaltySettleReqDto studyPenaltySettleReqDto) {
        List<Long> penalties = studyPenaltySettleReqDto.getPenalties();
        for(int i = 0; i<penalties.size(); i++){
            Long penaltyId = penalties.get(i);
            settleStudyGroupPenalty(penaltyId);
        }
    }
    public void exemptStudyGroupPenalty(Long penaltyId) {
        StudyPenalty studyPenalty = studyPenaltyRepository.findById(penaltyId).get();
        studyPenalty.changeToExempted();
    }
    public void cancelSettledStudyGroupPenalty(Long penaltyId) {
        StudyPenalty studyPenalty = studyPenaltyRepository.findById(penaltyId).get();
        studyPenalty.changeToNotSettled();

        int fine = studyPenalty.getFine();
        studyPenalty.getStudyGroup().minusTotalCost(fine);
        studyPenalty.getStudyMember().minusTotalPenalty(fine);
    }
}
