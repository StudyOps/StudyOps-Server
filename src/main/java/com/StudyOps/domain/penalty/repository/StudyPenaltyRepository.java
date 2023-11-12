package com.StudyOps.domain.penalty.repository;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.penalty.entity.StudyPenalty;
import com.StudyOps.domain.penalty.entity.StudyAbsentPenalty;
import com.StudyOps.domain.penalty.entity.StudyLatePenalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudyPenaltyRepository extends JpaRepository<StudyPenalty, Long> {
    List<StudyLatePenalty> findLatePenaltiesByStudyMember(StudyMember studyMember);
    List<StudyAbsentPenalty> findAbsentPenaltiesByStudyMember(StudyMember studyMember);
    List<StudyLatePenalty> findLatePenaltiesByStudyMemberAndIsSettledAndIsExempted(StudyMember studyMember, Boolean isSettled, Boolean isExempted);
    List<StudyAbsentPenalty> findAbsentPenaltiesByStudyMemberAndIsSettledAndIsExempted(StudyMember studyMember, Boolean isSettled, Boolean isExempted);
    List<StudyPenalty> findAllByStudyGroupAndDateAndIsSettledAndIsExempted(StudyGroup studyGroup, LocalDate date, Boolean isSettled, Boolean isExempted);
    List<StudyLatePenalty> findAllLatePenaltyByStudyGroupAndDateAndIsExempted(StudyGroup studyGroup, LocalDate date, Boolean isExempted);
    List<StudyAbsentPenalty> findAllAbsentPenaltyByStudyGroupAndDateAndIsExempted(StudyGroup studyGroup, LocalDate date, Boolean isExempted);
    Optional<StudyPenalty> findByStudyMemberAndDate(StudyMember studyMember, LocalDate date);
    List<StudyAbsentPenalty> findAllAbsentPenaltiesByStudyGroupAndDateBetweenAndIsSettledAndIsExempted(StudyGroup studyGroup, LocalDate start, LocalDate finish, Boolean isSettled, Boolean isExempted);
    List<StudyLatePenalty> findAllLatePenaltiesByStudyGroupAndDateBetweenAndIsSettledAndIsExempted(StudyGroup studyGroup, LocalDate start, LocalDate finish, Boolean isSettled, Boolean isExempted);
}
