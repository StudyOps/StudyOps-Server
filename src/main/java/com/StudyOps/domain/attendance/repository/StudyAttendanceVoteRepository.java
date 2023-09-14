package com.StudyOps.domain.attendance.repository;

import com.StudyOps.domain.attendance.entity.StudyAttendanceVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyAttendanceVoteRepository extends JpaRepository<StudyAttendanceVote, Long> {
}
