package com.StudyOps.domain.group.controller;

import com.StudyOps.domain.group.dto.StudyGroupCreateReqDto;
import com.StudyOps.domain.group.service.StudyGroupService;
import com.StudyOps.global.common.ApiResponse;
import com.StudyOps.global.common.ApiResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;
    @PostMapping("/groups/{userId}")
    public ResponseEntity<ApiResponse<Object>> createStudyGroup(@PathVariable(value = "userId") Long userId,@RequestBody StudyGroupCreateReqDto studyGroupCreateReqDto) {

        //로직 처리
        studyGroupService.createStudyGroup(userId,studyGroupCreateReqDto);
        //응답 처리
        ApiResponse<Object> successResponse = new ApiResponse<>(ApiResponseStatus.STUDY_GROUP_CREATE_SUCCESS);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @DeleteMapping("/groups/{groupId}/{userId}")
    public ResponseEntity<ApiResponse<Object>> quitStudyGroup(@PathVariable(value = "groupId") Long groupId, @PathVariable(value = "userId") Long userId){

        studyGroupService.quitStudyGroup(groupId,userId);

        ApiResponse<Object> successResponse = new ApiResponse<>(ApiResponseStatus.STUDY_GROUP_QUIT_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
