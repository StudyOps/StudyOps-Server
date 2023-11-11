package com.StudyOps.domain.penalty.repository;

import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.penalty.entity.StudyPenalty;
import com.StudyOps.domain.penalty.entity.StudyAbsentStudyPenalty;
import com.StudyOps.domain.penalty.entity.StudyLateStudyPenalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudyPenaltyRepository extends JpaRepository<StudyPenalty, Long> {
    List<StudyLateStudyPenalty> findLatePenaltiesByStudyMember(StudyMember studyMember);
    List<StudyAbsentStudyPenalty> findAbsentPenaltiesByStudyMember(StudyMember studyMember);
    List<StudyLateStudyPenalty> findLatePenaltiesByStudyMemberAndIsSettled(StudyMember studyMember, Boolean isSettled);
    List<StudyAbsentStudyPenalty> findAbsentPenaltiesByStudyMemberAndIsSettled(StudyMember studyMember, Boolean isSettled);
    Optional<StudyPenalty> findByStudyMemberAndDate(StudyMember studyMember, LocalDate date);
}
