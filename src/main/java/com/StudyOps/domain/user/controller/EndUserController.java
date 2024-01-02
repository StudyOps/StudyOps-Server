package com.StudyOps.domain.user.controller;

import com.StudyOps.domain.user.dto.EndUserEmailAndImageDto;
import com.StudyOps.domain.user.dto.EndUserInfoReqDto;
import com.StudyOps.domain.user.dto.EndUserPasswordChangeDto;
import com.StudyOps.domain.user.dto.EndUserResponseDto;
import com.StudyOps.domain.user.service.EndUserService;
import com.StudyOps.global.common.ApiResponse;
import com.StudyOps.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.StudyOps.global.common.ApiResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class EndUserController {
    private final EndUserService endUserService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<EndUserEmailAndImageDto>> findEndUserInfoById(){

        ApiResponse<EndUserEmailAndImageDto> successResponse = new ApiResponse<>(CURRENT_USER_GET_SUCCESS,endUserService.findEndUserInfoById(SecurityUtil.getCurrentMemberId()));
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    @GetMapping("/me/{email}")
    public ResponseEntity<ApiResponse<EndUserResponseDto>> findEndUserInfoByEmail(@PathVariable(value = "email") String email){

        ApiResponse<EndUserResponseDto> successResponse =  new ApiResponse<>(All_USER_INFO_GET_SUCCESS, endUserService.findEndUserInfoByEmail(email));
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<Object>> changePassword(@RequestBody EndUserPasswordChangeDto endUserPasswordChangeDto){
        endUserService.changePassword(endUserPasswordChangeDto,SecurityUtil.getCurrentMemberId());

        ApiResponse<Object> successResponse = new ApiResponse<>(CHANGE_PASSWORD_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    @PatchMapping("/me/info")
    public ResponseEntity<ApiResponse<Object>> changeEndUserInfo(@RequestBody EndUserInfoReqDto endUserInfoReqDto){
        endUserService.changeEndUserInfo(endUserInfoReqDto,SecurityUtil.getCurrentMemberId());

        ApiResponse<Object> successResponse = new ApiResponse<>(CHANGE_END_USER_INFO_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

}