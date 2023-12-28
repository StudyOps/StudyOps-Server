package com.StudyOps.security.controller;

import com.StudyOps.domain.user.dto.EndUserRequestDto;
import com.StudyOps.domain.user.dto.EndUserResponseDto;
import com.StudyOps.security.dto.TokenResDto;
import com.StudyOps.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<EndUserResponseDto> signup(@RequestBody EndUserRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResDto> login(@RequestBody EndUserRequestDto memberRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(memberRequestDto,response));
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<TokenResDto> reissue(@RequestHeader("Authorization") String accessToken, @CookieValue(value = "refreshToken") String refreshTokenReq, HttpServletResponse response) {
        return ResponseEntity.ok(authService.reissue(accessToken,refreshTokenReq,response));
    }
}