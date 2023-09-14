package com.StudyOps.domain.schedule.repository;

import com.StudyOps.domain.schedule.entity.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
}
