package com.StudyOps.domain.penalty.service;

import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.penalty.entity.StudyPenalty;
import com.StudyOps.domain.penalty.repository.StudyPenaltyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyPenaltyService {
    private final StudyPenaltyRepository studyPenaltyRepository;

    public void deleteStudyMember(StudyMember studyMember){
        List<StudyPenalty> penalties = studyPenaltyRepository.findAllByStudyMember(studyMember);
        penalties.stream()
                .forEach(penalty -> studyPenaltyRepository.delete(penalty));
    }
}
