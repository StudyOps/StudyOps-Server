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
    List<StudyLatePenalty> findLatePenaltiesByStudyMemberAndIsSettled(StudyMember studyMember, Boolean isSettled);
    List<StudyAbsentPenalty> findAbsentPenaltiesByStudyMemberAndIsSettled(StudyMember studyMember, Boolean isSettled);
    List<StudyPenalty> findAllByStudyGroupAndDate(StudyGroup studyGroup, LocalDate date);
    List<StudyLatePenalty> findAllLatePenaltyByStudyGroupAndDate(StudyGroup studyGroup, LocalDate date);
    List<StudyAbsentPenalty> findAllAbsentPenaltyByStudyGroupAndDate(StudyGroup studyGroup, LocalDate date);
    Optional<StudyPenalty> findByStudyMemberAndDate(StudyMember studyMember, LocalDate date);
}
