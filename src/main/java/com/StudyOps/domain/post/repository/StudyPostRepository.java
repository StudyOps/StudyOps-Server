package com.StudyOps.domain.post.repository;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.post.entity.StudyPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyPostRepository extends JpaRepository<StudyPost, Long> {
    Page<StudyPost> findAllByStudyGroupOrderByIdDesc(Pageable pageable, StudyGroup studyGroup);
    int countStudyPostByStudyGroup(StudyGroup studyGroup);
}
