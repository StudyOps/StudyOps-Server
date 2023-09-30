package com.StudyOps.domain.attendance.controller;

import com.StudyOps.domain.attendance.dto.StudyScheduleAndAttendanceResDto;
import com.StudyOps.domain.attendance.service.StudyAttendanceService;
import com.StudyOps.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.StudyOps.global.common.ApiResponseStatus.STUDY_DATE_ATTEND_SUCCESS;
import static com.StudyOps.global.common.ApiResponseStatus.STUDY_SCHEDULE_ATTENDANCE_GET_SUCCESS;

@RestController
@RequiredArgsConstructor
public class StudyAttendanceController {
    private final StudyAttendanceService studyAttendanceService;

    @PostMapping("/schedules/attendances/{groupId}/{userId}")
    public ResponseEntity<ApiResponse<Object>> createStudyGroup(@PathVariable(value = "groupId") Long groupId, @PathVariable(value = "userId") Long userId) {

        studyAttendanceService.attendStudyDate(groupId, userId);

        ApiResponse<Object> successResponse = new ApiResponse<>(STUDY_DATE_ATTEND_SUCCESS);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
    @GetMapping("/schedules/{groupId}/{userId}")
    public ResponseEntity<ApiResponse<StudyScheduleAndAttendanceResDto>> getStudyGroupScheduleAndAttendance(@PathVariable(value = "groupId") Long groupId, @PathVariable(value = "userId") Long userId) {

        ApiResponse<StudyScheduleAndAttendanceResDto> successResponse = new ApiResponse<>(STUDY_SCHEDULE_ATTENDANCE_GET_SUCCESS,studyAttendanceService.getStudyScheduleAndAttendance(groupId, userId));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}