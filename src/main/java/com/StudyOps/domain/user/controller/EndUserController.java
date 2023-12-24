package com.StudyOps.domain.user.controller;

import com.StudyOps.domain.user.dto.EndUserResponseDto;
import com.StudyOps.domain.user.service.EndUserService;
import com.StudyOps.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class EndUserController {
    private final EndUserService endUserService;

    @GetMapping("/me")
    public ResponseEntity<EndUserResponseDto> findMemberInfoById() {
        return ResponseEntity.ok(endUserService.findEndUserInfoById(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/{email}")
    public ResponseEntity<EndUserResponseDto> findMemberInfoByEmail(@PathVariable String email) {
        return ResponseEntity.ok(endUserService.findEndUserInfoByEmail(email));
    }
}