package com.StudyOps.domain.member.repository;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.member.entity.InvitedMember;
import com.StudyOps.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ref.Reference;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvitedMemberRepository extends JpaRepository<InvitedMember, Long> {
   List<InvitedMember> findAllByUser(User user);
   Optional<InvitedMember> findByStudyGroupAndUser(StudyGroup studyGroup, User user);
   List<InvitedMember> findAllByStudyGroup(StudyGroup studyGroup);
}
