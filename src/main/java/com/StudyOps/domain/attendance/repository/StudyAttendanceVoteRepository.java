package com.StudyOps.domain.attendance.repository;

import com.StudyOps.domain.attendance.entity.StudyAttendanceVote;
import com.StudyOps.domain.member.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyAttendanceVoteRepository extends JpaRepository<StudyAttendanceVote, Long> {
    List<StudyAttendanceVote> findAllByStudyMember(StudyMember studyMember);
}
