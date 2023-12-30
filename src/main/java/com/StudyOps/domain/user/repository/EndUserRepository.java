package com.StudyOps.domain.user.repository;

import com.StudyOps.domain.user.entity.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EndUserRepository extends JpaRepository<EndUser, Long> {
    Optional<EndUser> findByNickname(String nickname);

    Optional<EndUser> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickName);
}
