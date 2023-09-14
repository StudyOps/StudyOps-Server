package com.StudyOps.domain.board.repository;

import com.StudyOps.domain.board.entity.StudyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {
}
