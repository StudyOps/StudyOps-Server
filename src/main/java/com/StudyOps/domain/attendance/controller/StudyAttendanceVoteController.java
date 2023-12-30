package com.StudyOps.domain.attendance.controller;

import com.StudyOps.domain.attendance.service.StudyAttendanceVoteService;
import com.StudyOps.global.common.ApiResponse;
import com.StudyOps.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.StudyOps.global.common.ApiResponseStatus.STUDY_DATE_ABSENT_SUCCESS;


@RestController
@RequiredArgsConstructor
public class StudyAttendanceVoteController {
    private final StudyAttendanceVoteService studyAttendanceVoteService;

    @PatchMapping("/schedules/attendances/{groupId}")
    public ResponseEntity<ApiResponse<Object>> absentOrAttendStudyDate(@PathVariable(value = "groupId") Long groupId, @RequestParam String date, @RequestParam Boolean attendance) {

        studyAttendanceVoteService.absentOrAttendStudyDate(groupId, SecurityUtil.getCurrentMemberId(), date, attendance);

        ApiResponse<Object> successResponse = new ApiResponse<>(STUDY_DATE_ABSENT_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
