package com.StudyOps.security.controller;

import com.StudyOps.domain.user.dto.EndUserRequestDto;
import com.StudyOps.domain.user.dto.EndUserResponseDto;
import com.StudyOps.security.dto.TokenDto;
import com.StudyOps.security.dto.TokenRequestDto;
import com.StudyOps.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<EndUserResponseDto> signup(@RequestBody EndUserRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDto> login(@RequestBody EndUserRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}