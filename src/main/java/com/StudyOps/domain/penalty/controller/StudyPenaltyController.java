package com.StudyOps.domain.penalty.controller;

import com.StudyOps.domain.penalty.dto.StudyGroupNotSettledDayDto;
import com.StudyOps.domain.penalty.dto.StudyGroupPenaltyInfoByDateResDto;
import com.StudyOps.domain.penalty.dto.StudyGroupPenaltyInfoResDto;
import com.StudyOps.domain.penalty.service.StudyPenaltyService;
import com.StudyOps.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.StudyOps.global.common.ApiResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class StudyPenaltyController {
    private final StudyPenaltyService studyPenaltyService;

    @GetMapping("/penalty/{groupId}")
    public ResponseEntity<ApiResponse<StudyGroupPenaltyInfoResDto>> getStudyPenaltyInfo(@PathVariable(value = "groupId") Long groupId) {

        ApiResponse<StudyGroupPenaltyInfoResDto> successResponse = new ApiResponse<>(STUDY_PENALTY_INFO_GET_SUCCESS, studyPenaltyService.getStudyPenaltyInfo(groupId));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/penalty/{groupId}/dates")
    public ResponseEntity<ApiResponse<StudyGroupNotSettledDayDto>> getNotSettledDay(@PathVariable(value = "groupId") Long groupId){

        ApiResponse<StudyGroupNotSettledDayDto> successResponse = new ApiResponse<>(NOT_SETTLED_DAY_GET_SUCCESS, studyPenaltyService.getNotSettledDay(groupId));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/penalty/{groupId}/date")
    public ResponseEntity<ApiResponse<StudyGroupPenaltyInfoByDateResDto>> getPenaltyInfoByDate(@PathVariable(value = "groupId") Long groupId, @RequestParam LocalDate date){

        ApiResponse<StudyGroupPenaltyInfoByDateResDto> successResponse = new ApiResponse<>(PENALTY_INFO_BY_DATE_GET_SUCCESS, studyPenaltyService.getPenaltyInfoByDate(groupId,date));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
