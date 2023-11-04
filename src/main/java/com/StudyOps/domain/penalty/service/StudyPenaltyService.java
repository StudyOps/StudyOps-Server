package com.StudyOps.domain.penalty.service;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.penalty.dto.StudyGroupMemberPenaltyDto;
import com.StudyOps.domain.penalty.dto.StudyGroupPenaltyInfoResDto;
import com.StudyOps.domain.penalty.entity.StudyPenalty;
import com.StudyOps.domain.penalty.repository.StudyPenaltyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyPenaltyService {
    private final StudyPenaltyRepository studyPenaltyRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyMemberRepository studyMemberRepository;

    public void deleteStudyMember(StudyMember studyMember){
        List<StudyPenalty> penalties = studyPenaltyRepository.findAllByStudyMember(studyMember);
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
            List<StudyPenalty> notSettledPenalties = studyPenaltyRepository.findAllByStudyMemberAndIsSettled(studyMember, false);
            int penaltySum = 0;
            // 각 penalty정보에 대하여 해당 멤버의 정산되지 않은 누적 벌금을 구한다.
            for(int j=0; j<notSettledPenalties.size(); j++){
                StudyPenalty notSettledPenalty = notSettledPenalties.get(i);
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
}
