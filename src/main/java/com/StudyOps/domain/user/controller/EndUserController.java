package com.StudyOps.domain.user.controller;

import com.StudyOps.domain.user.dto.EndUserEmailAndImageDto;
import com.StudyOps.domain.user.dto.EndUserResponseDto;
import com.StudyOps.domain.user.service.EndUserService;
import com.StudyOps.global.common.ApiResponse;
import com.StudyOps.global.common.ApiResponseStatus;
import com.StudyOps.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class EndUserController {
    private final EndUserService endUserService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<EndUserEmailAndImageDto>> findEndUserInfoById(){

        ApiResponse<EndUserEmailAndImageDto> successResponse = new ApiResponse<>(ApiResponseStatus.CURRENT_USER_GET_SUCCESS,endUserService.findEndUserInfoById(SecurityUtil.getCurrentMemberId()));
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    @GetMapping("/me/{email}")
    public ResponseEntity<ApiResponse<EndUserResponseDto>> findEndUserInfoByEmail(@PathVariable(value = "email") String email){

        ApiResponse<EndUserResponseDto> successResponse =  new ApiResponse<>(ApiResponseStatus.All_USER_INFO_GET_SUCCESS, endUserService.findEndUserInfoByEmail(email));
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

}