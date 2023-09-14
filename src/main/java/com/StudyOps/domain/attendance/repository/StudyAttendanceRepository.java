package com.StudyOps.domain.attendance.repository;

import com.StudyOps.domain.attendance.entity.StudyAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StudyAttendanceRepository extends JpaRepository<StudyAttendance, Long> {
}
