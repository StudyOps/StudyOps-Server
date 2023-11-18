package com.StudyOps.domain.penalty.controller;

import com.StudyOps.domain.penalty.dto.*;
import com.StudyOps.domain.penalty.service.StudyPenaltyService;
import com.StudyOps.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<StudyGroupNotSettledDayDto>> getNotSettledDay(@PathVariable(value = "groupId") Long groupId) {

        ApiResponse<StudyGroupNotSettledDayDto> successResponse = new ApiResponse<>(NOT_SETTLED_DAY_GET_SUCCESS, studyPenaltyService.getNotSettledDay(groupId));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/penalty/{groupId}/date")
    public ResponseEntity<ApiResponse<StudyGroupPenaltyInfoByDateResDto>> getPenaltyInfoByDate(@PathVariable(value = "groupId") Long groupId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        ApiResponse<StudyGroupPenaltyInfoByDateResDto> successResponse = new ApiResponse<>(PENALTY_INFO_BY_DATE_GET_SUCCESS, studyPenaltyService.getPenaltyInfoByDate(groupId, date));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @PatchMapping("/penalty/{penaltyId}")
    public ResponseEntity<ApiResponse<Object>> settleStudyGroupPenalty(@PathVariable(value = "penaltyId") Long penaltyId) {

        studyPenaltyService.settleStudyGroupPenalty(penaltyId);

        ApiResponse<Object> successResponse = new ApiResponse<>(PENALTY_SETTLED_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/penalty/{groupId}/between")
    public ResponseEntity<ApiResponse<Object>> getPenaltyInfoByBetweenDate(@PathVariable(value = "groupId") Long groupId, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam("finish") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finish) {

        ApiResponse<Object> successResponse = new ApiResponse<>(PENALTY_INFO_BY_BETWEEN_DATE_GET_SUCCESS, studyPenaltyService.getPenaltyInfoByBetweenDate(groupId, start, finish));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @PatchMapping("/penalty")
    public ResponseEntity<ApiResponse<Object>> settleStudyGroupPenalties(@RequestBody StudyPenaltySettleReqDto studyPenaltySettleReqDto) {

        studyPenaltyService.settleStudyGroupPenalties(studyPenaltySettleReqDto);

        ApiResponse<Object> successResponse = new ApiResponse<>(PENALTY_SETTLED_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @DeleteMapping("/penalty/{penaltyId}")
    public ResponseEntity<ApiResponse<Object>> cancelSettledStudyGroupPenalty(@PathVariable Long penaltyId) {

        studyPenaltyService.cancelSettledStudyGroupPenalty(penaltyId);

        ApiResponse<Object> successResponse = new ApiResponse<>(PENALTY_SETTLED_CANCEL_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @PatchMapping("/penalty/exemption/{penaltyId}")
    public ResponseEntity<ApiResponse<Object>> exemptStudyGroupPenalties(@PathVariable Long penaltyId) {

        studyPenaltyService.exemptStudyGroupPenalty(penaltyId);

        ApiResponse<Object> successResponse = new ApiResponse<>(PENALTY_EXEMPT_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/penalty/counts/{groupId}")
    public ResponseEntity<ApiResponse<List<StudyGroupPenaltyCountsDto>>> getStudyPenaltyCounts(@PathVariable(value = "groupId") Long groupId) {

        ApiResponse<List<StudyGroupPenaltyCountsDto>> successResponse = new ApiResponse<>(PENALTY_COUNTS_GET_SUCCESS, studyPenaltyService.getStudyPenaltyCounts(groupId));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
