package com.StudyOps.domain.post.repository;

import com.StudyOps.domain.post.entity.StudyPostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPostFileRepository extends JpaRepository<StudyPostFile, Long> {

}
