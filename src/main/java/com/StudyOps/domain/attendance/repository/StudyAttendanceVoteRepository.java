package com.StudyOps.domain.attendance.repository;

import com.StudyOps.domain.attendance.entity.StudyAttendanceVote;
import com.StudyOps.domain.member.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudyAttendanceVoteRepository extends JpaRepository<StudyAttendanceVote, Long> {
    List<StudyAttendanceVote> findAllByStudyMember(StudyMember studyMember);
    Optional<StudyAttendanceVote> findByStudyMemberAndDate(StudyMember studyMember, LocalDate date);
}
