package com.StudyOps.domain.penalty.repository;

import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.penalty.entity.Penalty;
import com.StudyOps.domain.penalty.entity.StudyAbsentPenalty;
import com.StudyOps.domain.penalty.entity.StudyLatePenalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    List<StudyLatePenalty> findLatePenaltiesByStudyMember(StudyMember studyMember);
    List<StudyAbsentPenalty> findAbsentPenaltiesByStudyMember(StudyMember studyMember);
    List<StudyLatePenalty> findLatePenaltiesByStudyMemberAndIsSettled(StudyMember studyMember, Boolean isSettled);
    List<StudyAbsentPenalty> findAbsentPenaltiesByStudyMemberAndIsSettled(StudyMember studyMember, Boolean isSettled);
}
