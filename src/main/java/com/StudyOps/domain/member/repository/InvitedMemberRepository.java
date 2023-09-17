package com.StudyOps.domain.member.repository;

import com.StudyOps.domain.member.entity.InvitedMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitedMemberRepository extends JpaRepository<InvitedMember, Long> {
}
