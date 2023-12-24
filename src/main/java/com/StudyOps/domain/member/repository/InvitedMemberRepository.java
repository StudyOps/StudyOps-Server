package com.StudyOps.domain.member.repository;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.member.entity.InvitedMember;
import com.StudyOps.domain.user.entity.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitedMemberRepository extends JpaRepository<InvitedMember, Long> {
   List<InvitedMember> findAllByEndUser(EndUser endUser);
   Optional<InvitedMember> findByStudyGroupAndEndUser(StudyGroup studyGroup, EndUser endUser);
   List<InvitedMember> findAllByStudyGroup(StudyGroup studyGroup);
}
