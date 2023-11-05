package com.StudyOps.domain.attendance.controller;

import com.StudyOps.domain.attendance.dto.StudyAttendanceAndAbsenceDto;
import com.StudyOps.domain.attendance.dto.StudyScheduleAndAttendanceResDto;
import com.StudyOps.domain.attendance.service.StudyAttendanceService;
import com.StudyOps.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.StudyOps.global.common.ApiResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class StudyAttendanceController {
    private final StudyAttendanceService studyAttendanceService;

    @PostMapping("/schedules/attendances/{groupId}/{userId}")
    public ResponseEntity<ApiResponse<Object>> attendStudyDate(@PathVariable(value = "groupId") Long groupId, @PathVariable(value = "userId") Long userId) {

        studyAttendanceService.attendStudyDate(groupId, userId);

        ApiResponse<Object> successResponse = new ApiResponse<>(STUDY_DATE_ATTEND_SUCCESS);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @GetMapping("/schedules/{groupId}/{userId}")
    public ResponseEntity<ApiResponse<StudyScheduleAndAttendanceResDto>> getStudyGroupScheduleAndAttendance(@PathVariable(value = "groupId") Long groupId, @PathVariable(value = "userId") Long userId) {

        ApiResponse<StudyScheduleAndAttendanceResDto> successResponse = new ApiResponse<>(STUDY_SCHEDULE_ATTENDANCE_GET_SUCCESS, studyAttendanceService.getStudyScheduleAndAttendance(groupId, userId));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/schedules/attendances/{groupId}/{userId}")
    public ResponseEntity<ApiResponse<StudyAttendanceAndAbsenceDto>> getStudyAttendanceByDate(@PathVariable(value = "groupId") Long groupId, @PathVariable(value = "userId") Long userId, @RequestParam String date) {


        ApiResponse<StudyAttendanceAndAbsenceDto> successResponse = new ApiResponse<>(STUDY_ATTENDANCE_GET_BY_DATE_SUCCESS, studyAttendanceService.getStudyAttendanceByDate(groupId, userId, date));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
