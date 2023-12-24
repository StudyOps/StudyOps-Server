package com.StudyOps.domain.user.service;

import com.StudyOps.domain.user.dto.EndUserResponseDto;
import com.StudyOps.domain.user.repository.EndUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EndUserService {
    private final EndUserRepository endUserRepository;

    public EndUserResponseDto findEndUserInfoById(Long endUserId) {
        return endUserRepository.findById(endUserId)
                .map(EndUserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public EndUserResponseDto findEndUserInfoByEmail(String email) {
        return endUserRepository.findByEmail(email)
                .map(EndUserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

}
