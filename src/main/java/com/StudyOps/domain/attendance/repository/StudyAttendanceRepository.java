package com.StudyOps.domain.attendance.repository;

import com.StudyOps.domain.attendance.entity.StudyAttendance;
import com.StudyOps.domain.member.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StudyAttendanceRepository extends JpaRepository<StudyAttendance, Long> {
    List<StudyAttendance> findAllByStudyMember(StudyMember studyMember);
}
