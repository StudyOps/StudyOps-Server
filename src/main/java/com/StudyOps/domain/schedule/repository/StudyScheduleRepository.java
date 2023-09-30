package com.StudyOps.domain.schedule.repository;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.schedule.entity.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
    List<StudySchedule> findAllByStudyGroup(StudyGroup studyGroup);

    Optional<StudySchedule> findByStudyGroupAndDayWeek(StudyGroup studyGroup, String curretDayWeek);
}
