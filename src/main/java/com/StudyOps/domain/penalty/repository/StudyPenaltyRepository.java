package com.StudyOps.domain.penalty.repository;

import com.StudyOps.domain.attendance.entity.StudyAttendanceVote;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.penalty.entity.StudyPenalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyPenaltyRepository extends JpaRepository<StudyPenalty, Long> {
    List<StudyPenalty> findAllByStudyMember(StudyMember studyMember);
    List<StudyPenalty> findAllByStudyMemberAndIsSettled(StudyMember studyMember, Boolean isSettled);
}
