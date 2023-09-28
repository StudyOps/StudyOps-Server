package com.StudyOps.domain.member.repository;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    Optional<StudyMember> findByStudyGroupAndUser(StudyGroup studyGroup, User user);
    List<StudyMember> findAllByUser(User user);
}
