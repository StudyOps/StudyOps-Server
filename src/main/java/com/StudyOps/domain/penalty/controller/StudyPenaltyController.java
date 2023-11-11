package com.StudyOps.domain.penalty.controller;

import com.StudyOps.domain.group.dto.StudyGroupResDto;
import com.StudyOps.domain.penalty.dto.StudyGroupPenaltyInfoResDto;
import com.StudyOps.domain.penalty.service.StudyPenaltyService;
import com.StudyOps.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.StudyOps.global.common.ApiResponseStatus.STUDY_PENALTY_INFO_GET_SUCCESS;

@RestController
@RequiredArgsConstructor
public class StudyPenaltyController {
    private final StudyPenaltyService studyPenaltyService;

    @GetMapping("/penalty/{groupId}")
    public ResponseEntity<ApiResponse<StudyGroupPenaltyInfoResDto>> getStudyPenaltyInfo(@PathVariable(value = "groupId") Long groupId) {

        ApiResponse<StudyGroupPenaltyInfoResDto> successResponse = new ApiResponse<>(STUDY_PENALTY_INFO_GET_SUCCESS, studyPenaltyService.getStudyPenaltyInfo(groupId));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}