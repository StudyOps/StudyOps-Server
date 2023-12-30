package com.StudyOps.domain.group.controller;

import com.StudyOps.domain.group.dto.*;
import com.StudyOps.domain.group.service.StudyGroupService;
import com.StudyOps.global.common.ApiResponse;
import com.StudyOps.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.StudyOps.global.common.ApiResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    //스터디 생성
    @PostMapping("/groups")
    public ResponseEntity<ApiResponse<StudyGroupCreatedIdDto>> createStudyGroup(@RequestBody StudyGroupReqDto studyGroupReqDto) {

        //응답 처리
        ApiResponse<StudyGroupCreatedIdDto> successResponse = new ApiResponse<>(STUDY_GROUP_CREATE_SUCCESS, studyGroupService.createStudyGroup(SecurityUtil.getCurrentMemberId(), studyGroupReqDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    //스터디 탈퇴
    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<ApiResponse<Object>> quitStudyGroup(@PathVariable(value = "groupId") Long groupId) {

        studyGroupService.quitStudyGroup(groupId, SecurityUtil.getCurrentMemberId());

        ApiResponse<Object> successResponse = new ApiResponse<>(STUDY_GROUP_QUIT_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    //스터디 전체 조회
    @GetMapping("/groups")
    public ResponseEntity<ApiResponse<List<StudyGroupResDto>>> getAllOfStudyGroups() {

        ApiResponse<List<StudyGroupResDto>> successResponse = new ApiResponse<>(ALL_STUDY_GROUPS_GET_SUCCESS, studyGroupService.getAllStudyGroups(SecurityUtil.getCurrentMemberId()));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    //스터디 정보 조회
    @GetMapping("/info/{groupId}")
    public ResponseEntity<ApiResponse<StudyGroupInfoResDto>> getStudyGroupInfo(@PathVariable(value = "groupId") Long groupId) {

        ApiResponse<StudyGroupInfoResDto> successResponse = new ApiResponse<>(STUDY_GROUP_INFO_GET_SUCCESS, studyGroupService.getStudyGroupInfo(groupId));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    @PatchMapping("/info/rules/{groupId}")
    public ResponseEntity<ApiResponse<Object>> changeStudyGroupRule(@PathVariable (value = "groupId") Long groupId, @RequestBody StudyGroupRuleReqDto studyGroupRuleReqDto){

        studyGroupService.changeStudyGroupRule(groupId,studyGroupRuleReqDto);
        ApiResponse<Object> successResponse = new ApiResponse<>(STUDY_GROUP_RULE_CHANGE_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    @PatchMapping("/info/intro/{groupId}")
    public ResponseEntity<ApiResponse<Object>> changeStudyGroupInto(@PathVariable (value = "groupId") Long groupId, @RequestBody StudyGroupIntroReqDto studyGroupIntroReqDto){

        studyGroupService.changeStudyGroupIntro(groupId,studyGroupIntroReqDto);
        ApiResponse<Object> successResponse = new ApiResponse<>(STUDY_GROUP_INTRO_CHANGE_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    @PatchMapping("/info/accounts/{groupId}")
    public ResponseEntity<ApiResponse<Object>> changeStudyGroupAccount(@PathVariable (value = "groupId") Long groupId, @RequestBody StudyGroupAccountReqDto studyGroupAccountReqDto){

        studyGroupService.changeStudyGroupAccount(groupId, studyGroupAccountReqDto);
        ApiResponse<Object> successResponse = new ApiResponse<>(STUDY_GROUP_ACCOUNT_CHANGE_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
