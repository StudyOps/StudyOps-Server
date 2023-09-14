package com.StudyOps.domain.penalty.repository;

import com.StudyOps.domain.penalty.entity.StudyPenalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPenaltyRepository extends JpaRepository<StudyPenalty, Long> {
}
