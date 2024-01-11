package com.StudyOps.domain.user.service;

import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.user.dto.EndUserInfoReqDto;
import com.StudyOps.domain.user.dto.EndUserPasswordChangeDto;
import com.StudyOps.domain.user.dto.EndUserResponseDto;
import com.StudyOps.domain.user.entity.EndUser;
import com.StudyOps.domain.user.repository.EndUserRepository;
import com.StudyOps.global.common.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EndUserService {
    private final EndUserRepository endUserRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final PasswordEncoder passwordEncoder;

    public EndUserResponseDto findEndUserInfoById(Long endUserId) {
        EndUser endUser = endUserRepository.findById(endUserId).get();

        return EndUserResponseDto.builder()
                .email(endUser.getEmail())
                .profileImageUrl(endUser.getProfileImageUrl())
                .nickName(endUser.getNickname())
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

    public void changePassword(EndUserPasswordChangeDto endUserPasswordChangeDto, Long userId) {
        EndUser endUser = endUserRepository.findById(userId).get();

        if(passwordEncoder.matches(endUserPasswordChangeDto.getOldPassword(),endUser.getPassword()))
            endUser.changePassword(passwordEncoder.encode(endUserPasswordChangeDto.getNewPassword()));
        else
            throw new CustomRuntimeException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    public void changeEndUserInfo(EndUserInfoReqDto endUserInfoReqDto, Long userId) {
        EndUser endUser = endUserRepository.findById(userId).get();

        if(endUserRepository.existsByNickname(endUserInfoReqDto.getNickName()))
            throw new CustomRuntimeException("이미 사용중인 닉네임 입니다.", HttpStatus.CONFLICT);
        else {
            String newNickname = endUser.changeEndUserInfo(endUserInfoReqDto);
            List<StudyMember> studyMemberList = studyMemberRepository.findAllByEndUserAndHostStatus(endUser,true);
            for(int i=0; i<studyMemberList.size(); i++){
                studyMemberList.get(i).getStudyGroup().changeHostName(newNickname);
            }
        }
    }

    public void changeEndUserProfileImage(String url, Long userId) {
        EndUser endUser = endUserRepository.findById(userId).get();


    }
}
