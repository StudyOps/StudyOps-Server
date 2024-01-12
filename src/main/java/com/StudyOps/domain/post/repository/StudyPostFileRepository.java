package com.StudyOps.domain.post.repository;

import com.StudyOps.domain.post.entity.StudyPost;
import com.StudyOps.domain.post.entity.StudyPostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyPostFileRepository extends JpaRepository<StudyPostFile, Long> {

    List<StudyPostFile> findAllByStudyPost(StudyPost studyPost);
}
