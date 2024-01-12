package com.StudyOps.domain.post.repository;

import com.StudyOps.domain.post.entity.StudyPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPostRepository extends JpaRepository<StudyPost, Long> {
}
