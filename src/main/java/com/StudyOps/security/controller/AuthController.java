package com.StudyOps.security.controller;

import com.StudyOps.domain.user.dto.EndUserRequestDto;
import com.StudyOps.domain.user.dto.EndUserResponseDto;
import com.StudyOps.global.common.ApiResponse;
import com.StudyOps.global.common.ApiResponseStatus;
import com.StudyOps.security.dto.TokenDto;
import com.StudyOps.security.dto.TokenResDto;
import com.StudyOps.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.StudyOps.global.common.ApiResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<EndUserResponseDto>> signup(@RequestBody EndUserRequestDto memberRequestDto) {
        ApiResponse<EndUserResponseDto> successResponse = new ApiResponse<>(SIGN_UP_SUCCESS,authService.signup(memberRequestDto));
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<TokenResDto>> login(@RequestBody EndUserRequestDto memberRequestDto, HttpServletResponse response) {
        ApiResponse<TokenResDto> successResponse = new ApiResponse<>(LOGIN_SUCCESS,authService.login(memberRequestDto,response));
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<ApiResponse<TokenResDto>> reissue(@RequestHeader("Authorization") String accessToken, @CookieValue(value = "refreshToken") String refreshTokenReq, HttpServletResponse response) {
        ApiResponse<TokenResDto> successResponse = new ApiResponse<>(ACCESS_TOKEN_REISSUE_SUCCESS,authService.reissue(accessToken,refreshTokenReq,response));
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}