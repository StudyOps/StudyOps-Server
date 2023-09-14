package com.StudyOps.domain.member.repository;

import com.StudyOps.domain.member.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
}
