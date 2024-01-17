package com.StudyOps.domain.member.repository;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.user.entity.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    Optional<StudyMember> findByStudyGroupAndEndUser(StudyGroup studyGroup, EndUser endUser);
    List<StudyMember> findAllByEndUser(EndUser endUser);
    List<StudyMember> findAllByStudyGroup(StudyGroup studyGroup);
    List<StudyMember> findAllByEndUserAndHostStatus(EndUser endUser, Boolean hostStatus);
    boolean existsByStudyGroupAndEndUser(StudyGroup studyGroup, EndUser endUser);
}
