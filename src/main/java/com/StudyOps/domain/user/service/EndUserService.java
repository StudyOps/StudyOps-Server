package com.StudyOps.domain.user.service;

import com.StudyOps.domain.user.dto.EndUserEmailAndImageDto;
import com.StudyOps.domain.user.dto.EndUserResponseDto;
import com.StudyOps.domain.user.entity.EndUser;
import com.StudyOps.domain.user.repository.EndUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EndUserService {
    private final EndUserRepository endUserRepository;

    public EndUserEmailAndImageDto findEndUserInfoById(Long endUserId) {
        EndUser endUser = endUserRepository.findById(endUserId).get();

        return EndUserEmailAndImageDto.builder()
                .email(endUser.getEmail())
                .profileImageUrl(endUser.getProfileImageUrl())
                .build();
    }

    public EndUserResponseDto findEndUserInfoByEmail(String email) {
        EndUser endUser = endUserRepository.findByEmail(email).get();

        return EndUserResponseDto.builder()
                .email(endUser.getEmail())
                .nickName(endUser.getNickname())
                .profileImageUrl(endUser.getProfileImageUrl())
                .build();
    }

}
